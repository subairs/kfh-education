package com.kfh.education.request;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;


/**
 * 
 * @author subair
 * Dto Class representing a authentication request.
 */
public class AuthRequestDTO {

	// Username of registered user.
	// String is not null and has at least one non-whitespace character.
	// 
	@NotBlank(message = "Username cannot be blank!")
    private String username;
	
	
	// Password of registered user.
	@NotBlank(message = "Password cannot be blank!")
    private String password;

	
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
 
}