package com.kfh.education.request;

/**
 * 
 * @author subair
 * Dto class representing course request.
 */
public class CourseRequest {

	// Name of the course
	private String name;
	
	// Course description
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
