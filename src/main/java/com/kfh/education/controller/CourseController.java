package com.kfh.education.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kfh.education.exception.GlobalExceptionHandler;
import com.kfh.education.request.CourseRequest;
import com.kfh.education.response.CourseResponse;
import com.kfh.education.service.CourseService;
import com.kfh.education.serviceimpl.CourseServiceImpl;

/**
 * 
 * @author subair Controller class representing Course controller
 */

@RestController
@RequestMapping("course")
public class CourseController {

	private final CourseService courseService;

	/**
	 * 
	 * @param courseService
	 */
	public CourseController(CourseService courseService) {
		this.courseService = courseService;
	}
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CourseController.class);



	@GetMapping("/all")
	public ResponseEntity<List<CourseResponse>> getAllCourses() {
		List<CourseResponse> courseResponses = courseService.getAllCourses();
		LOGGER.info(""+ courseResponses);
		return ResponseEntity.status(HttpStatus.OK).body(courseResponses);
	}

	@PostMapping("/create")
	public ResponseEntity<CourseResponse> createCourse(@RequestBody CourseRequest courseRequest) {
		CourseResponse courseResponse = courseService.createCourse(courseRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(courseResponse);
	}

	@GetMapping("/{courseId}")
	public ResponseEntity<CourseResponse> getCourseById(@PathVariable long courseId){
		
		CourseResponse courseResponse= courseService.getCourseById(courseId);
		return ResponseEntity.status(HttpStatus.CREATED).body(courseResponse);
		
	}
	
	@PutMapping("/update/{courseId}")
	public ResponseEntity<CourseResponse> updateCourse(
			@RequestBody CourseRequest courseRequest, 
			@PathVariable long courseId){
		CourseResponse courseResponse= courseService.updateCourse(courseId, courseRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(courseResponse);
	}
	

	@DeleteMapping("/{courseId}")
	public ResponseEntity<CourseResponse> deleteCourseById(@PathVariable long courseId){
		
		CourseResponse courseResponse= courseService.deleteCourseById(courseId);
		return ResponseEntity.status(HttpStatus.OK).body(courseResponse);
		
	}
}
