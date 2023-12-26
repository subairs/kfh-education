package com.kfh.education.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;

import com.kfh.education.entity.Course;
import com.kfh.education.entity.Student;
import com.kfh.education.exception.CustomException;
import com.kfh.education.exception.ErrorHandlerMessage;
import com.kfh.education.exception.NotFoundException;
import com.kfh.education.exception.ServerSideException;
import com.kfh.education.repository.CourseRepository;
import com.kfh.education.repository.StudentRepository;
import com.kfh.education.request.CourseRequest;
import com.kfh.education.response.StudentCourseResponse;
import com.kfh.education.response.StudentResponse;
import com.kfh.education.service.StudentCourseService;

import jakarta.persistence.EntityNotFoundException;

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

	private static final Logger LOGGER = LoggerFactory.getLogger(StudentCourseServiceImpl.class);

	@Override
	public StudentCourseResponse alocateCourseForStudent(long studentId, long courseId) {
		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new EntityNotFoundException("Student not found with ID: " + studentId));
		Course course = courseRepository.findById(courseId)
				.orElseThrow(() -> new EntityNotFoundException("Course not found with ID: " + courseId));
		student.setCourse(course);
		
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
			LOGGER.error(ex.getMessage());
			throw new CustomException(ErrorHandlerMessage.SAVING_DB);
		}

		// Map the existing Student to Student dto
		return modelMapper.map(createdStudent, StudentCourseResponse.class);

	}

	@Override
	public List<StudentResponse> getAllStudentsByCourse(long courseId) {
		List<Student> students = null;
		try {
			students = studentRepository.findAllByCourseId(courseId);
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
			throw new CustomException(ErrorHandlerMessage.RETRIEVING_DB);
			
		}
		if (students.isEmpty()) {
			LOGGER.warn("Student not found with Course ID: " + courseId);
			throw new NotFoundException("Student not found with Course ID: " + courseId);
		}

		// Map the existing Student to Student dto
		return students.stream().map(student -> modelMapper.map(student, StudentResponse.class))
				.collect(Collectors.toList());

	}

	@Override
	public StudentCourseResponse updateCourseForStudent(long studentId, long courseId) {
		Student student = studentRepository.findById(studentId)
				.orElseThrow(() -> new EntityNotFoundException("Student not found with ID: " + studentId));

		Course course = courseRepository.findById(courseId)
				.orElseThrow(() -> new EntityNotFoundException("Course not found with ID: " + courseId));

		student.setCourse(course);
		Student updatedStudent = null;
		try {
			if (student != null && course != null) {
				// Save the Course entity using Student repository
				updatedStudent = studentRepository.save(student);

			}

		} catch (InternalServerError ex) {
			LOGGER.error(ex.getMessage());
			throw new ServerSideException(ex.getMessage());
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
			// Catch any other unexpected exceptions
			// throw new CustomException("An error occurred during student save: " +
			// ex.getMessage());
			throw new CustomException(ErrorHandlerMessage.SAVING_DB);
		}

		// Map the existing Student to Student dto
		return modelMapper.map(updatedStudent, StudentCourseResponse.class);

	}

}
