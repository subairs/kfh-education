package com.kfh.education.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kfh.education.validation.ArabicCharacters;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 
 * @author subair
 * Dto class representing a student request
 */
public class StudentRequest {
	
	// English name of a student
	@NotBlank(message = "English name cannot be blank!")
	@Size(min=2, message="Contains atleast 2 characters")
	@JsonProperty("name") // Specify the display name for API/documentation
	private String enName;
	
	@NotBlank(message = "Arabic name cannot be blank!")
	// CustomAnothation for validate arabic characters
	@ArabicCharacters(message = "Arabic name should contain only Arabic Characters!" )
	
	@JsonProperty("arabicName") // Specify the display name for API/documentation
	// Arabic name of a student
	
	private String arName;
	
	// Email of a student
	@Email(message = "Please provide a valid email address!")
	private String email;
	
	// mobile number or land line number
	@NotBlank(message = "Telephone cannot be blank!")
	private String teliphone;
	
	
	// address of a student
	// @NotBlank(message="Address cannot be blank!")
	private AddressRequest address;

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getArName() {
		return arName;
	}

	public void setArName(String arName) {
		this.arName = arName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTeliphone() {
		return teliphone;
	}

	public void setTeliphone(String teliphone) {
		this.teliphone = teliphone;
	}

	public AddressRequest getAddress() {
		return address;
	}

	public void setAddress(AddressRequest address) {
		this.address = address;
	}

	
}
