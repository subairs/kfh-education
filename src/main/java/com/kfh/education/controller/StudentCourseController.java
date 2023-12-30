package com.kfh.education.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kfh.education.request.CourseRequest;
import com.kfh.education.request.StudentRequest;
import com.kfh.education.response.StudentCourseResponse;
import com.kfh.education.response.StudentResponse;
import com.kfh.education.service.StudentCourseService;
import com.kfh.education.service.StudentService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 * 
 * @author subair
 * 
 * Controller class representing StudentCourse controller.
 */
@RestController
@RequestMapping("student-course")
@SecurityRequirement(name = "kfh-education")
public class StudentCourseController {

	private final StudentCourseService studentCourseService;

	/**
	 * Constructor based injection for StudentCourseService.
	 * 
	 * @param studentService The service for StudentCourse.
	 * 
	 */
	public StudentCourseController(StudentCourseService studentCourseService) {
		this.studentCourseService = studentCourseService;
	}

	// Logger for StudentController
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);

	/**
	 * Allocate course for a student.
	 * 
	 * @param studentId The ID of the Student to allocate course.
	 * @param courseId  The ID of the Course for allocate student in this course.
	 * @return ResponseEntity with Student , Course and HTTP status code.
	 */
	@PostMapping("/allocate-course-for-student")
	public ResponseEntity<StudentCourseResponse> allocateCourseForStudent(@RequestParam long studentId,
			@RequestParam long courseId) {
		StudentCourseResponse studentCourseResponse = studentCourseService.allocateCourseForStudent(studentId, courseId);
		return ResponseEntity.status(HttpStatus.CREATED).body(studentCourseResponse);
	}

	/**
	 * Retrieve all students details by a course.
	 * 
	 * @param courseId The ID of the Course.
	 * @return ResponseEntity with a list of students and an HTTP status code.
	 */
	@GetMapping("/all-students-by-course/{courseId}")
	public ResponseEntity<List<StudentResponse>> getAllStudentsByCourseId(@PathVariable long courseId) {
		List<StudentResponse> studentResponses = studentCourseService.getAllStudentsByCourse(courseId);

		return ResponseEntity.status(HttpStatus.OK).body(studentResponses);

	}

	/**
	 * Update course for a Student.
	 * 
	 * @param studentId The ID for Student.
	 * @param courseId  The Id for Course.
	 * @return ResponseEntity with updated Student and course details with status
	 *         code.
	 */
	@PutMapping("/update-course-for-student")
	public ResponseEntity<StudentCourseResponse> updateCourseByStudent(@RequestParam long studentId,
			@RequestParam long courseId) {
		StudentCourseResponse studentCourseResponse = studentCourseService.updateCourseForStudent(studentId, courseId);
		return ResponseEntity.status(HttpStatus.CREATED).body(studentCourseResponse);
	}

}
