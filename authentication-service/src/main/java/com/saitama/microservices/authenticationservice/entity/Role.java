package com.saitama.microservices.authenticationservice.entity;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "role")
public class Role /*implements GrantedAuthority*/ {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1740849488233051041L;
	
    
    @Id
	@GeneratedValue(
	    strategy = GenerationType.SEQUENCE,
	    generator = "role_generator"
	)
	@SequenceGenerator(
	    name = "role_generator",
	    sequenceName = "role_seq",
	    allocationSize = 3
	)
    @Column
    private Long id;
    
    @Enumerated(EnumType.STRING)
    @Column
    private Authority authority;
    
    @ManyToMany
    @JoinTable(
			name = "roles_privileges",
			joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id")
	)
    private Collection<Privilege> privileges;
    
    @ManyToMany(mappedBy = "authorities")
    private Collection<User> users;
	
	public Role() { }

	public Role(Authority authority) {
		this.authority = authority;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public String getAuthority() {
		return this.authority.name();
	}
	
	public Authority getAuthorityEnum() {
		return this.authority;
	}
	
	public void setAuthority(Authority authority) {
		this.authority = authority;
	}
	
	public void addPrivilege(Privilege privilege) {
		if (this.privileges == null) {
			this.privileges = new HashSet<Privilege>();
		}
		this.privileges.add(privilege);
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authority == null) ? 0 : authority.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((privileges == null) ? 0 : privileges.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		if (authority != other.authority)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (privileges == null) {
			if (other.privileges != null)
				return false;
		} else if (!privileges.equals(other.privileges))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", authority=" + authority + ", privileges=" + privileges + "]";
	}
	
	
}
