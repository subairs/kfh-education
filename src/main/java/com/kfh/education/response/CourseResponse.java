package com.kfh.education.response;

/**
 * 
 * @author subair Dto class Representing a course response
 */

public class CourseResponse {
	private long id;

	// Name of the course
	private String name;

	// Course description
	private String description;

	public CourseResponse() {

	}

	public CourseResponse(long id, String name, String description) {
		super();
		this.id = id;
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
