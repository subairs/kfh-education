package com.kfh.education.request;

import jakarta.validation.constraints.NotBlank;

/**
 * 
 * @author subair
 * Dto class representing course request.
 */
public class CourseUpdateRequest {
	private long id;
	// Name of the course
	@NotBlank(message = "Course name cannot be blank!")
	private String name;
	
	// Course description
	private String description;

	public CourseUpdateRequest() {
		
	}

	public CourseUpdateRequest(long id, @NotBlank(message = "Course name cannot be blank!") String name, String description) {
		super();
		this.id=id;
		this.name = name;
		this.description = description;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


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
