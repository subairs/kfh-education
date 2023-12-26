package com.kfh.education.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kfh.education.request.CourseRequest;
import com.kfh.education.request.StudentRequest;
import com.kfh.education.response.StudentCourseResponse;
import com.kfh.education.response.StudentResponse;
import com.kfh.education.service.StudentCourseService;
import com.kfh.education.service.StudentService;

@RestController
@RequestMapping("student-course")
public class StudentCourseController {
	
	
	private final StudentCourseService studentCourseService;

	/**
	 * 
	 * @param studentService
	 */
	public StudentCourseController(StudentCourseService studentCourseService) {
		this.studentCourseService = studentCourseService;
	}
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);

	@PutMapping("/allocate-course-for-student/{studentId}/{courseId}")
	public ResponseEntity<StudentCourseResponse> alocateCourseForStudent(
			@PathVariable long studentId, 
			@PathVariable long courseId){
		StudentCourseResponse studentCourseResponse= studentCourseService.alocateCourseForStudent(studentId, courseId);
		return ResponseEntity.status(HttpStatus.CREATED).body(studentCourseResponse);
	}
	
	@GetMapping("/all-students-by-course/{courseId}")
	public ResponseEntity<List<StudentResponse>> getAllStudentsByCourseId(
			@PathVariable long courseId){
		List<StudentResponse> studentResponses= studentCourseService.getAllStudentsByCourse(courseId);
		 
		return ResponseEntity.status(HttpStatus.OK).body(studentResponses);
		
		
	}
	
	@PutMapping("/course-update-by-student/{studentId}/{courseId}")
	public ResponseEntity<StudentCourseResponse> updateCourseByStudent(
			@PathVariable long studentId,
			@PathVariable long courseId){
		StudentCourseResponse studentCourseResponse= studentCourseService.updateCourseForStudent(studentId, courseId);
		return ResponseEntity.status(HttpStatus.CREATED).body(studentCourseResponse);
	}
	
	
	
	
	
	

}
