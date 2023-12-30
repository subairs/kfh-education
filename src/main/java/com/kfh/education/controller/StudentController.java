package com.kfh.education.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kfh.education.request.StudentRequest;
import com.kfh.education.response.CourseResponse;
import com.kfh.education.response.StudentResponse;
import com.kfh.education.service.StudentService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

/**
 * 
 * @author subair
 * Controller class representing student controller
 */
@RestController
@RequestMapping("student")
@Validated
@SecurityRequirement(name = "kfh-education")
public class StudentController {
	
	private final StudentService studentService;

	/**
	 * Constructor-based dependency injection for StudentService.
	 * 
	 * @param studentService The service for Student.
	 */
	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);


	/**
	 * Retrieve all Student.
	 * 
	 * @return ResponseEntity with a list of students and an HTTP status code.
	 */
	@GetMapping("/get-all")
	public ResponseEntity<List<StudentResponse>> getAllStudents() {
		List<StudentResponse> studentResponses = studentService.getAllStudents();
		LOGGER.info(""+ studentResponses);
		return ResponseEntity.status(HttpStatus.OK).body(studentResponses);
	}

	/**
	 * Create a new Student.
	 * 
	 * @param studentRequest The details of the new Student created.
	 * @return ResponseEntity with the created Student details and HTTP status code.
	 */
	@PostMapping("/register")
	public ResponseEntity<StudentResponse> createStudent(@RequestBody @Valid StudentRequest studentRequest) {
		StudentResponse studentResponse = studentService.createStudent(studentRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(studentResponse);
	}

	/**
	 * Retrieve a Student by studentId.
	 * 
	 * @param studentId ID of the Student to retrieve.
	 * @return ResponseEntity with Student details and the HTTP status code.
	 */
	@GetMapping("get/{studentId}")
	public ResponseEntity<StudentResponse> getStudentById(@PathVariable long studentId){
		
		StudentResponse studentResponse= studentService.getStudentById(studentId);
		return ResponseEntity.status(HttpStatus.CREATED).body(studentResponse);
		
	}
	
	
	/**
	 * Update an existing Student by courseId.
	 * 
	 * @param studentRequest The updated details of Student.
	 * @param studentId The ID of the Student to update. 
	 * @return ResponseEntity with updated Student details and an HTTP status code.
	 */
	@PutMapping("/update/{studentId}")
	public ResponseEntity<StudentResponse> updateStudent(
			@RequestBody @Valid StudentRequest studentRequest, 
			@PathVariable long studentId){
		StudentResponse studentResponse= studentService.updateStudent(studentId, studentRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(studentResponse);
	}
	
	/**
	 * Delete a Student by courseId.
	 * 
	 * @param studentId The ID of the Student to delete.
	 * @return ResponseEntity with Deleted Student details and status code. 
	 */
	@DeleteMapping("delete/{studentId}")
	public ResponseEntity<StudentResponse> deleteStudentById(@PathVariable long studentId){
		StudentResponse studentResponse= studentService.deleteStudentById(studentId);
		return ResponseEntity.status(HttpStatus.OK).body(studentResponse);
		
	}

}
