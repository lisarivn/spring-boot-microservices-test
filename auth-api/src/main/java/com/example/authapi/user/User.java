package com.example.authapi.user;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

//Marks this class as a JPA entity mapped to a database table
@Entity
//Maps this entity to the "users" table
@Table(name = "users")
public class User {
	// Marks this field as the primary key
	@Id
	// Automatically generates a unique identifier for each user
	@GeneratedValue
	private UUID id;
	// Email is required and must be unique in the database
	@Column(nullable = false, unique = true)
	private String email;
	
	// Stores the BCrypt hash instead of the raw password
	@Column(name = "password_hash", nullable = false)
	private String passwordHash;
	
	// Required by JPA for entity creation
	public User() {}
	
	public User(String email, String passwordHash) {
		this.email = email;
		this.passwordHash = passwordHash;
	}
	
	public UUID getId() {
		return id;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPasswordHash() {
		return passwordHash;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
}
