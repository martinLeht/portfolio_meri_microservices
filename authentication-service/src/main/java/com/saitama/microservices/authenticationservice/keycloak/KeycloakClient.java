package com.saitama.microservices.authenticationservice.keycloak;

import java.util.Arrays;
import java.util.Optional;

import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RoleMappingResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.saitama.microservices.authenticationservice.constant.Role;
import com.saitama.microservices.commonlib.exception.CommonInternalException;

import io.micrometer.core.lang.NonNull;

@Service
public class KeycloakClient {
	
	private static final Logger LOG = LoggerFactory.getLogger(KeycloakClient.class);
	
	@Value("${keycloak.server-url}")
	private String serverUrl;

	@Value("${keycloak.client-id}")
	private String clientId;
	
	@Value("${keycloak.client-secret}")
	private String clientSecret;
	
	@Value("${keycloak.realm}")
	private String realm;
	
	@Value("${keycloak.token-uri}")
    private String keycloakLogin;
	
	@Value("${keycloak.logout}")
    private String keycloakLogout;
	
	private final Keycloak keycloak;
	
	@Autowired
	public KeycloakClient(Environment env) {
		this.keycloak = KeycloakBuilder.builder()
			    .serverUrl(env.getRequiredProperty("keycloak.server-url"))
			    .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
			    .realm(env.getRequiredProperty("keycloak.realm"))
			    .clientId(env.getRequiredProperty("keycloak.client-id"))
			    .clientSecret(env.getRequiredProperty("keycloak.client-secret"))
			    .resteasyClient(
			        new ResteasyClientBuilder()
			            .connectionPoolSize(10).build()
			    ).build();
	}
	
	
	public UserRepresentation registerTemporaryUser(@NonNull String username, String password) {
		Optional<UserRepresentation> existingUser = getUserByUsername(username);
		
		if (existingUser.isPresent()) {
			throw new CommonInternalException("user-exists", "User already exists with username: " + username, HttpStatus.CONFLICT);
		}
		
		keycloak.tokenManager().getAccessToken();
		
        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(username);
        user.setEmailVerified(false);

        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersRessource = realmResource.users();

        // Create user (requires manage-users role)
        Response response = usersRessource.create(user);
        LOG.info("Repsonse: {} {}", response.getStatus(), response.getStatusInfo());
        LOG.info(response.getLocation().toString());
        String userId = CreatedResponseUtil.getCreatedId(response);

        LOG.info("User created with userId: {}", userId);

        // Define password credential
        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(password);

        UserResource userResource = usersRessource.get(userId);

        // Set password credential
        userResource.resetPassword(passwordCred);

        // Get realm role "temporary_user" (requires view-realm role)
        RoleRepresentation temporaryUserRealmRole = realmResource.roles().get(Role.TEMPORARY_USER.getValue()).toRepresentation();
        
        userResource.roles().realmLevel()
                .add(Arrays.asList(temporaryUserRealmRole));
        
        return userResource.toRepresentation();
	}
	
	public void verifyTemporaryUserEmail(String userId, String username) {
		keycloak.tokenManager().getAccessToken();
		
		UserRepresentation user = getUserByUsername(username).orElseThrow(
				() -> new CommonInternalException("user-not-found", "User not found with username: " + username, HttpStatus.NOT_FOUND));
		
		if (!user.getId().equals(userId)) {
			throw new CommonInternalException("user-not-found", "User id mismatch when querying users", HttpStatus.NOT_FOUND);
		}
		
		UsersResource usersResource = keycloak.realm(realm).users();
		UserResource userRerource = usersResource.get(user.getId());

		RoleMappingResource roleMapping = userRerource.roles();
		
		if (hasRole(roleMapping, Role.TEMPORARY_USER)) {
			user.setEmailVerified(true);
	        usersResource.get(user.getId()).update(user);
		} else {
			throw new CommonInternalException("user-role-not-found", "User does not have temporary_user role", HttpStatus.NOT_FOUND);
		}	
		
	}
	
	public AccessTokenResponse loginTemporaryUser(String username, String password) {
		UserRepresentation user = getUserByUsername(username).orElseThrow(
				() -> new CommonInternalException("user-not-found", "User not found with username: " + username, HttpStatus.NOT_FOUND));
		
		if (!user.isEmailVerified()) {
			throw new CommonInternalException("user-not-verified", "User is not verified", HttpStatus.FORBIDDEN);
		}
		
		UsersResource usersResource = keycloak.realm(realm).users();
		UserResource userRerource = usersResource.get(user.getId());

		RoleMappingResource roleMapping = userRerource.roles();
		
		if (hasRole(roleMapping, Role.TEMPORARY_USER)) {
			try {
				logoutTemporaryUser(user.getId(), user.getUsername());
			} catch (Exception e) {
				LOG.error("Something went wrong in user logout");
				throw new CommonInternalException("failed-user-login", "Failed to login user beause previous session logout failed", HttpStatus.INTERNAL_SERVER_ERROR);
			}
			Keycloak instance = Keycloak.getInstance(serverUrl, realm, username, password, clientId, clientSecret);
			return instance.tokenManager().getAccessToken();	
		} else {
			throw new CommonInternalException("user-role-not-found", "User does not have temporary_user role", HttpStatus.NOT_FOUND);
		}
	}
	
	public void logoutTemporaryUser(String userId, String username) throws Exception {
		keycloak.tokenManager().getAccessToken();
		
		UserRepresentation user = getUserByUsername(username).orElseThrow(
				() -> new CommonInternalException("user-not-found", "User not found with username: " + username, HttpStatus.NOT_FOUND));
		
		if (!user.getId().equals(userId)) {
			throw new CommonInternalException("user-not-found", "User id mismatch when querying users", HttpStatus.NOT_FOUND);
		}
		
		UsersResource usersResource = keycloak.realm(realm).users();
		UserResource userRerource = usersResource.get(user.getId());

		RoleMappingResource roleMapping = userRerource.roles();
		
		if (hasRole(roleMapping, Role.TEMPORARY_USER)) {
			userRerource.logout();	
		} else {
			throw new CommonInternalException("user-role-not-found", "User does not have temporary_user role", HttpStatus.NOT_FOUND);
		}		
    }
	
	private boolean hasRole(RoleMappingResource roleMapping, Role role) {
		return roleMapping.getAll().getRealmMappings().stream()
				.anyMatch(r -> r.getName().equals(role.getValue()));
	}
	
	
	public Optional<UserRepresentation> getUserByUsername(String username) {
		keycloak.tokenManager().getAccessToken();
		return keycloak.realm(realm).users().search(username).stream().findFirst();
	}
	
}
