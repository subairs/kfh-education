package com.kfh.education.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

/**
 * 
 * @author subair
 * Controller class representing student controller
 */
@RestController
@RequestMapping("student")
public class StudentController {
	
	private final StudentService studentService;

	/**
	 * 
	 * @param studentService
	 */
	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);



	@GetMapping("/all")
	public ResponseEntity<List<StudentResponse>> getAllStudents() {
		List<StudentResponse> studentResponses = studentService.getAllStudents();
		LOGGER.info(""+ studentResponses);
		return ResponseEntity.status(HttpStatus.OK).body(studentResponses);
	}

	@PostMapping("/create")
	public ResponseEntity<StudentResponse> createStudent(@RequestBody StudentRequest studentRequest) {
		StudentResponse studentResponse = studentService.createStudent(studentRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(studentResponse);
	}

	@GetMapping("/{studentId}")
	public ResponseEntity<StudentResponse> getStudentById(@PathVariable long studentId){
		
		StudentResponse studentResponse= studentService.getStudentById(studentId);
		return ResponseEntity.status(HttpStatus.CREATED).body(studentResponse);
		
	}
	
	@PutMapping("/update/{studentId}")
	public ResponseEntity<StudentResponse> updateStudent(
			@RequestBody StudentRequest studentRequest, 
			@PathVariable long studentId){
		StudentResponse studentResponse= studentService.updateStudent(studentId, studentRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(studentResponse);
	}
	
	@DeleteMapping("/{studentId}")
	public ResponseEntity<StudentResponse> deleteStudentById(@PathVariable long studentId){
		StudentResponse studentResponse= studentService.deleteStudentById(studentId);
		return ResponseEntity.status(HttpStatus.OK).body(studentResponse);
		
	}

}
