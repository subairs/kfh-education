package com.kfh.education.service;

import org.springframework.stereotype.Service;

import com.kfh.education.response.StudentCourseResponse;

/**
 * 
 * @author subair
 * Service interface representing course allocation for student.
 */

@Service
public interface StudentCourseService {
	
	StudentCourseResponse alocateCourseForStudent(long studentId, long courseId);
	

}
