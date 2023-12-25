package com.kfh.education.request;

import com.kfh.education.entity.Address;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

/**
 * 
 * @author subair
 * Dto class representing a student request
 */
public class StudentRequest {
	// English name of a student
	private String enName;
	
	// Arabic name of a student
	private String arName;
	
	// Email of a student
	private String email;
	
	// mobile number or land line number
	private String teliphone;
	
	// address of a student
	private Address address;

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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	
}
