package com.kfh.education.serviceimpl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;

import com.kfh.education.entity.Address;
import com.kfh.education.entity.Course;
import com.kfh.education.entity.Student;
import com.kfh.education.exception.CustomException;
import com.kfh.education.exception.ErrorHandlerMessage;
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

	/**
	 * Constructor-based dependency injection for StudentRepository.
	 * 
	 * @param studentRepository The repository for Student Entities.
	 * @param modelMapper      map 'entity to dto' or 'dto to entity' .
	 */
	@Autowired // Optional for constructor injection
	public StudentServiceImpl(StudentRepository studentRepository, ModelMapper modelMapper) {

		this.studentRepository = studentRepository;
		this.modelMapper = modelMapper;
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);

	
	/**
	 * Create new Course based on the provided StudentRequest.
	 * 
	 * @param studentRequest the Dto containing the details of the Student to be
	 *                      created.
	 * 
	 * @return The Dto of the the created Student.
	 * 
	 * @throws ServersideException if there is an issue facing server side.
	 * 
	 * @throws CustomException     if there is an issue with creating Course.
	 */
	@Override
	public StudentResponse createStudent(StudentRequest studentRequest) {
		LOGGER.info("***** CourseService -> Create Course Started - " + new Date());

		// Map the CourseRequest to an Student Entity
		Student student = modelMapper.map(studentRequest, Student.class);
		Student createdStudent = null;
		try {

			// Save the Course entity using Student repository
			createdStudent = studentRepository.save(student);
		} catch (InternalServerError ex) {
			throw new ServerSideException(ex.getMessage());
		} catch (Exception ex) {
			// Catch any other unexpected exceptions
			// throw new CustomException("An error occurred during student save: " + ex.getMessage());
			LOGGER.error(ex.getMessage());
			throw new CustomException(ErrorHandlerMessage.SAVING_DB);
		}

		LOGGER.info("***** StudentService -> Create Student End - " + new Date());
		// Map the saved Course entity back to an Course dto
		return modelMapper.map(createdStudent, StudentResponse.class);
	}

	/**
	 * Retrieve a Student Dto by the provided Student id.
	 * 
	 * @param studentId The Id of course to be retrieve .
	 * 
	 * @return the Student dto of the Retrieved Student.
	 * 
	 * @throws EntityNotFoundException if there is an issue with not found course
	 *                                 with provided id.
	 * @throws  CustomException                              
	 */
	@Override
	public StudentResponse getStudentById(long studentId) {
		// Use the StudentRepository to find the Student entity by id.
		Student student = studentRepository.findById(studentId)

				.orElseThrow(() -> new EntityNotFoundException("Student not found with ID: " + studentId));

		// Map the retrieved Student entity to a Student dto
		return modelMapper.map(student, StudentResponse.class);
	}

	/**
	 * Update the details of existing Student based on the provided Student dto and
	 * course id.
	 * 
	 * @param studentId      The id of the Student to be updated.
	 * 
	 * @param studentRequest the dto containing updated details of the Student.
	 * 
	 * @throws EntityNotFoundException if the Course with the given id is not found.
	 */
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
		Student updatedStudent = null;
		try {
			// Save the updated Student entity using the Student repository.
			updatedStudent = studentRepository.save(existingStudent);
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
			throw new CustomException(ErrorHandlerMessage.SAVING_DB);
		}
		// Map the updated Student entity back to a Student dto
		return modelMapper.map(updatedStudent, StudentResponse.class);
	}

	/**
	 * Delete a Student by provided Student id.
	 * 
	 * @param studentId The id of the Student to be deleted.
	 * 
	 * @return The existing Student Dto
	 * 
	 * @throws EntityNotFoundException if the course with the given id is not found.
	 * 
	 * @throws CustomException         if there is an issue with deleting the
	 *                                 Course.
	 */
	@Override
	public StudentResponse deleteStudentById(long studentId) {
		// Find the Student entity by student id
		Student existingStudent = studentRepository.findById(studentId)
				.orElseThrow(() -> new EntityNotFoundException("Student not found with ID: " + studentId));
		try {

			// Delete the Student entity using the Student repository
			studentRepository.delete(existingStudent);
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
			throw new CustomException(ErrorHandlerMessage.DELETE_DB);
		}

		// Map the existing Student to Student dto
		return modelMapper.map(existingStudent, StudentResponse.class);
	}

	/**
	 * Retrieves a list of all students from the course repository then map into list
	 * of StudentResponse.
	 * 
	 * @return List of Student Dtos representing all Students.
	 * 
	 * @throws NotFoundException if the retrieved student list is empty.
	 */
	@Override
	public List<StudentResponse> getAllStudents() {
		List<Student> students = null;
		try {
			// Use the courseRepository to fetch all Course entities
			students = studentRepository.findAll();
			if (students.isEmpty()) {
				LOGGER.warn("Students information not in students table.");
				throw new NotFoundException(ErrorHandlerMessage.NOT_FOUND);
			}
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
			throw new CustomException(ErrorHandlerMessage.RETRIEVING_DB);
		}
		// Map the list of Student entities to a list of Student Dtos
		return students.stream().map(student -> modelMapper.map(student, StudentResponse.class))
				.collect(Collectors.toList());
	}

}
