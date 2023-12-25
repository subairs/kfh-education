package com.kfh.education.serviceimpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;

import com.kfh.education.entity.Course;
import com.kfh.education.entity.Student;
import com.kfh.education.exception.CustomException;
import com.kfh.education.exception.ServerSideException;
import com.kfh.education.repository.CourseRepository;
import com.kfh.education.repository.StudentRepository;
import com.kfh.education.response.StudentCourseResponse;
import com.kfh.education.response.StudentResponse;
import com.kfh.education.service.StudentCourseService;

/**
 * 
 * @author subair Service class representing StudentCourseService.
 * @see StudentCourseService
 */

@Service
public class StudentCourseServiceImpl implements StudentCourseService {

	private final StudentRepository studentRepository;
	private final CourseRepository courseRepository;
	private final ModelMapper modelMapper;

	@Autowired
	public StudentCourseServiceImpl(StudentRepository studentRepository, CourseRepository courseRepository,
			ModelMapper modelMapper) {
		this.studentRepository = studentRepository;
		this.courseRepository = courseRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public StudentCourseResponse alocateCourseForStudent(long studentId, long courseId) {
		Student student = studentRepository.findById(studentId).orElse(null);
		Course course = courseRepository.findById(courseId).orElse(null);
		student.setCourse(course);
		// studentRepository.save(student);

		Student createdStudent = null;
		try {
			if (student != null && course != null) {
				// Save the Course entity using Student repository
				createdStudent = studentRepository.save(student);

			}

		} catch (InternalServerError ex) {
			throw new ServerSideException(ex.getMessage());
		} catch (Exception ex) {
			// Catch any other unexpected exceptions
			throw new CustomException("An error occurred during student save: " + ex.getMessage());
		}

		// Map the existing Student to Student dto
		return modelMapper.map(createdStudent, StudentCourseResponse.class);

	}

}
