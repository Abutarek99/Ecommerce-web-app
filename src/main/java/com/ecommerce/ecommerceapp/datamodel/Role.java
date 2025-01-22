package com.ecommerce.ecommerceapp.datamodel;

import java.util.Collection;
import java.util.HashSet;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	
	@ManyToMany(mappedBy = "roles")
	private Collection<LocalUser> users = new HashSet<>();
	
	public Role() {
		super();
	}
	
	public Role(Long id, String name, Collection<LocalUser> users) {
		super();
		this.id = id;
		this.name = name;
		this.users = users;
	}

	public Role(String name) {
		super();
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<LocalUser> getUsers() {
		return users;
	}

	public void setUsers(Collection<LocalUser> users) {
		this.users = users;
	}

}
