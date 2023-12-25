package com.kfh.education.response;

import com.kfh.education.entity.Address;

/**
 * 
 * @author subair
 * Dto class representing a student response
 */
public class StudentResponse {
	
	// id of a student
	private long id;
	
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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
