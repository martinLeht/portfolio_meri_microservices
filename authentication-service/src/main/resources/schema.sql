CREATE TABLE IF NOT EXISTS temp_user (
	id bigint NOT NULL, 
	access_uuid uuid NOT NULL UNIQUE,
	keycloak_user_id VARCHAR(255) NOT NULL UNIQUE,
	username VARCHAR(255), 
	uuid uuid NOT NULL, 
	locked BOOLEAN, 
	require_verification_on_every_access BOOLEAN,
	created_at TIMESTAMP,
	updated_at TIMESTAMP,
	verification_token uuid UNIQUE, 
	verified BOOLEAN, 
	verified_at TIMESTAMP, 
	PRIMARY KEY (id)
);