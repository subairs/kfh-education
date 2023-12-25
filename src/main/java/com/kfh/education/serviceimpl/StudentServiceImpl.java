package com.kfh.education.serviceimpl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;

import com.kfh.education.entity.Address;
import com.kfh.education.entity.Course;
import com.kfh.education.entity.Student;
import com.kfh.education.exception.CustomException;
import com.kfh.education.exception.NotFoundException;
import com.kfh.education.exception.ServerSideException;
import com.kfh.education.repository.StudentRepository;
import com.kfh.education.request.StudentRequest;
import com.kfh.education.response.CourseResponse;
import com.kfh.education.response.StudentResponse;
import com.kfh.education.service.StudentService;

import jakarta.persistence.EntityNotFoundException;

/**
 * 
 * @author subair Service class representing for student services
 * @see StudentService
 */

@Service
public class StudentServiceImpl implements StudentService {

	private final StudentRepository studentRepository;
	private final ModelMapper modelMapper;

	public StudentServiceImpl(StudentRepository studentRepository, ModelMapper modelMapper) {

		this.studentRepository = studentRepository;
		this.modelMapper = modelMapper;
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);

	@Override
	public StudentResponse createStudent(StudentRequest studentRequest) {
		LOGGER.info("***** CourseService -> Create Course Started - " + new Date());

		// Map the CourseRequest to an Student Entity
		Student student = modelMapper.map(studentRequest, Student.class);
		Student createdStudent;
		try {

			// Save the Course entity using Student repository
			createdStudent = studentRepository.save(student);
		} catch (InternalServerError ex) {
			throw new ServerSideException(ex.getMessage());
		} catch (Exception ex) {
			// Catch any other unexpected exceptions
			throw new CustomException("An error occurred during student save: " + ex.getMessage());
		}

		LOGGER.info("***** StudentService -> Create Student End - " + new Date());
		// Map the saved Course entity back to an Course dto
		return modelMapper.map(createdStudent, StudentResponse.class);
	}

	@Override
	public StudentResponse getStudentById(long studentId) {
		// Use the StudentRepository to find the Student entity by id.
		Student student = studentRepository.findById(studentId)

				.orElseThrow(() -> new EntityNotFoundException("Student not found with ID: " + studentId));

		// Map the retrieved Student entity to a Student dto
		return modelMapper.map(student, StudentResponse.class);
	}

	@Override
	public StudentResponse updateStudent(long studentId, StudentRequest studentRequest) {
		// Use the courseRepository to find the Student entity by id.
		Student existingStudent = studentRepository.findById(studentId)
				.orElseThrow(() -> new EntityNotFoundException("Course not found with ID: " + studentId));
		existingStudent.setEnName(studentRequest.getEnName());
		existingStudent.setArName(studentRequest.getArName());
		existingStudent.setEmail(studentRequest.getEmail());
		existingStudent.setTeliphone(studentRequest.getTeliphone());
		Address existingAddress = existingStudent.getAddress();
		existingAddress.setCity(studentRequest.getAddress().getCity());
		existingAddress.setCountry(studentRequest.getAddress().getCountry());
		existingAddress.setHouseName(studentRequest.getAddress().getHouseName());
		existingAddress.setPostalCode(studentRequest.getAddress().getPostalCode());
		existingAddress.setState(studentRequest.getAddress().getState());
		existingAddress.setStreet(studentRequest.getAddress().getStreet());
		existingStudent.setAddress(existingAddress);

		// modelMapper.map(studentRequest, existingStudent);

		// Save the updated Student entity using the Student repository.
		Student updatedStudent = studentRepository.save(existingStudent);

		// Map the updated Student entity back to a Student dto
		return modelMapper.map(updatedStudent, StudentResponse.class);
	}

	@Override
	public StudentResponse deleteStudentById(long studentId) {
		// Find the Student entity by student id
		Student existingStudent = studentRepository.findById(studentId)
				.orElseThrow(() -> new EntityNotFoundException("Student not found with ID: " + studentId));
		try {
			
			// Delete the Student entity using the Student repository
			studentRepository.delete(existingStudent);
		}catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
		
		// Map the existing Student to Student dto
		return modelMapper.map(existingStudent, StudentResponse.class);
	}

	@Override
	public List<StudentResponse> getAllStudents() {
		// Use the courseRepository to fetch all Course entities
		List<Student> students = studentRepository.findAll();
		if (students.isEmpty()) {
			throw new NotFoundException("No Students found");
		}
		// Map the list of Student entities to a list of Student Dtos
		return students.stream().map(student -> modelMapper.map(student, StudentResponse.class))
				.collect(Collectors.toList());
	}
	

}
