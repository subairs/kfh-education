package com.kfh.education.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kfh.education.request.StudentRequest;
import com.kfh.education.response.StudentResponse;

/**
 * 
 * @author subair
 * Service interface representing student services
 */

@Service
public interface StudentService {
	
	StudentResponse createStudent(StudentRequest studentRequest);
	
	StudentResponse getStudentById(long studentId);
	
	StudentResponse updateStudent(long studentId, StudentRequest studentRequest);
	
	StudentResponse deleteStudentById(long studentId);
	
	List<StudentResponse> getAllStudents();

}
