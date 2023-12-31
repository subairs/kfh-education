package com.kfh.education.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kfh.education.request.CourseRequest;
import com.kfh.education.request.CourseUpdateRequest;
import com.kfh.education.response.CourseResponse;


/**
 * 
 * @author subair
 * Service interface representing course services
 */

@Service
public interface CourseService {
	
	CourseResponse createCourse(CourseRequest courseRequest);
	
	CourseResponse getCourseById(long courseId);
	
	CourseResponse updateCourse(long courseId, CourseUpdateRequest courseRequest);
	
	boolean deleteCourseById(long courseId);
	
	List<CourseResponse> getAllCourses();
	
	

}
