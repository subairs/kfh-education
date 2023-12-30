package com.kfh.education.request;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegistrationRequest {
	
	@NotBlank(message = "Username cannot be blank!")
	@Size(min=3, max=15, message ="Username must be between 3 and 15 characters!")
    private String username;
    
	@NotBlank(message = "Password cannot be blank!")
	@Size(min=3, max=15, message ="Password must be between 3 and 15 characters!")
	private String password;
	
	
	@NotBlank(message = "Role cannot be blank!")
    private Set<String> roles;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Set<String> getRoles() {
		return roles;
	}
	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}


}