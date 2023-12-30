package com.kfh.education.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

/*
 * Entity class representing a student
 */

@Entity
public class Student {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	// English name of the student
	@Column(nullable = false)
	private String enName;
	
	// Arabic name of the student
	@Column(nullable = false)
	private String arName;
	
	// Email of the student
	@Column(nullable = false)
	private String email;
	
	// mobile number or land line number
	@Column(nullable = false)
	private String teliphone;
	
	// Address associated with the student
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false)
	private Address address;
	
	// Courses associated with the student
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "course_id", referencedColumnName = "id")
	private Course course;

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

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
	
}
