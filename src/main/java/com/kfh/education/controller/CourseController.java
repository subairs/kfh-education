package com.kfh.education.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kfh.education.request.CourseRequest;
import com.kfh.education.response.CourseResponse;
import com.kfh.education.service.CourseService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 * 
 * @author subair Controller class representing Course controller.
 * 
 */

@RestController
@RequestMapping("course")
@SecurityRequirement(name = "kfh-education")
public class CourseController {

	private final CourseService courseService;

	/**
	 * Constructor-based dependency injection for CourseService.
	 * 
	 * @param courseService The service for Course.
	 */
	@Autowired // Optional for constructor injection
	public CourseController(CourseService courseService) {
		this.courseService = courseService;
	}
	
	// CourseController based Logger
	private static final Logger LOGGER = LoggerFactory.getLogger(CourseController.class);


	/**
	 * Retrieve all Courses
	 * 
	 * @return ResponseEntity with a list of courses and an HTTP status code.
	 */
	@GetMapping("/get-all")
	public ResponseEntity<List<CourseResponse>> getAllCourses() {
		// Fetch all courses
		List<CourseResponse> courseResponses = courseService.getAllCourses();
		LOGGER.info(""+ courseResponses);
		return ResponseEntity.status(HttpStatus.OK).body(courseResponses);
	}
	/**
	 * Create a new Course.
	 * 
	 * @param courseRequest The details of the new Course created.
	 * @return ResponseEntity with the created Course details and HTTP status code.
	 */
	@PostMapping("/create")
	public ResponseEntity<CourseResponse> createCourse(@RequestBody CourseRequest courseRequest) {
		CourseResponse courseResponse = courseService.createCourse(courseRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(courseResponse);
	}

	/**
	 * Retrieve a Course by courseId.
	 * 
	 * @param courseId ID of the course to retrieve.
	 * @return ResponseEntity with course details and the HTTP status code.
	 */
	@GetMapping("get/{courseId}")
	public ResponseEntity<CourseResponse> getCourseById(@PathVariable long courseId){
		
		CourseResponse courseResponse= courseService.getCourseById(courseId);
		return ResponseEntity.status(HttpStatus.CREATED).body(courseResponse);
		
	}
	/**
	 * Update an existing course by courseId.
	 * 
	 * @param courseRequest The updated details of course.
	 * @param courseId The ID of the course to update. 
	 * @return ResponseEntity with updated course details and an HTTP status code.
	 */
	@PutMapping("/update/{courseId}")
	public ResponseEntity<CourseResponse> updateCourse(
			@RequestBody CourseRequest courseRequest, 
			@PathVariable long courseId){
		CourseResponse courseResponse= courseService.updateCourse(courseId, courseRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(courseResponse);
	}
	
	/**
	 * Delete a course by courseId.
	 * 
	 * @param courseId The ID of the course to delete.
	 * @return ResponseEntity with Deleted Course details and status code. 
	 */
	@DeleteMapping("delete/{courseId}")
	public ResponseEntity<CourseResponse> deleteCourseById(@PathVariable long courseId){
		
		CourseResponse courseResponse= courseService.deleteCourseById(courseId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(courseResponse);
		
	}
}
