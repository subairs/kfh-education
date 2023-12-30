package com.kfh.education.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kfh.education.request.CourseRequest;
import com.kfh.education.response.StudentCourseResponse;
import com.kfh.education.response.StudentResponse;

/**
 * 
 * @author subair
 * Service interface representing course allocation for student.
 */

@Service
public interface StudentCourseService {
	
	StudentCourseResponse allocateCourseForStudent(long studentId, long courseId);

	List<StudentResponse> getAllStudentsByCourse(long courseId);

	StudentCourseResponse updateCourseForStudent(long studentId, long courseId);
	

}
