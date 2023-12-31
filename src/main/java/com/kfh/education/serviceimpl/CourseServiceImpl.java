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

import com.kfh.education.entity.Course;
import com.kfh.education.exception.CustomException;
import com.kfh.education.exception.ErrorHandlerMessage;
import com.kfh.education.exception.NotFoundException;
import com.kfh.education.exception.ServerSideException;
import com.kfh.education.repository.CourseRepository;
import com.kfh.education.request.CourseRequest;
import com.kfh.education.request.CourseUpdateRequest;
import com.kfh.education.response.CourseResponse;
import com.kfh.education.service.CourseService;

import jakarta.persistence.EntityNotFoundException;

/**
 * 
 * @author subair Service class representing course services.
 * @see CourseService
 * 
 *      Service class responsible for business logic related to Course
 *      operations.
 * 
 */
@Service
public class CourseServiceImpl implements CourseService {

	private final CourseRepository courseRepository;
	private final ModelMapper modelMapper;

	/**
	 * Constructor-based dependency injection for CourseRepository.
	 * 
	 * @param courseRepository The repository for Course Entities.
	 * @param modelMapper      map 'entity to dto' or 'dto to entity' .
	 */
	@Autowired // Optional for constructor injection
	public CourseServiceImpl(CourseRepository courseRepository, ModelMapper modelMapper) {
		this.courseRepository = courseRepository;
		this.modelMapper = modelMapper;
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(CourseServiceImpl.class);

	/**
	 * Create new Course based on the provided CourseRequest.
	 * 
	 * @param courseRequest the Dto containing the details of the Course to be
	 *                      created.
	 * 
	 * @return The Dto of the the created Course.
	 * 
	 * @throws ServersideException if there is an issue facing server side.
	 * 
	 * @throws CustomException     if there is an issue with creating Course.
	 */
	@Override
	public CourseResponse createCourse(CourseRequest courseRequest) {
		LOGGER.info("***** CourseService -> Create Course Started - " + new Date());

		// Map the CourseRequest to an Course Entity
		Course course = modelMapper.map(courseRequest, Course.class);
		Course createdCourse;
		try {

			// Save the Course entity using course repository
			createdCourse = courseRepository.save(course);
		} catch (InternalServerError ex) {
			LOGGER.error(ex.getMessage());
			throw new ServerSideException(ex.getMessage());
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
			// Catch any other unexpected exceptions
			// throw new CustomException("An error occurred during course save: " +
			// ex.getMessage());
			throw new CustomException(ErrorHandlerMessage.SAVING_DB);
		}

		LOGGER.info("***** CourseService -> Create Course End - " + new Date());
		// Map the saved Course entity back to an Course dto
		return modelMapper.map(createdCourse, CourseResponse.class);

	}

	/**
	 * Retrieve a Course Dto by the provided course id.
	 * 
	 * @param courseId The Id of course to be retrieve .
	 * 
	 * @return the Course dto of the Retrieved course.
	 * 
	 * @throws EntityNotFoundException if there is an issue with not found course
	 *                                 with provided id.
	 */
	@Override
	public CourseResponse getCourseById(long courseId) {

		// Use the courseRepository to find the Course entity by id.
		Course course = courseRepository.findById(courseId)

				.orElseThrow(() -> new EntityNotFoundException("Course not found with ID: " + courseId));

		// Map the retrieved Course entity to a Course dto
		return modelMapper.map(course, CourseResponse.class);
	}

	/**
	 * Update the details of existing Course based on the provided Course dto and
	 * course id.
	 * 
	 * @param courseId      The id of the Course to be updated.
	 * 
	 * @param courseRequest the dto containing updated details of the Course.
	 * 
	 * @throws EntityNotFoundException if the Course with the given id is not found.
	 */
	@Override
	public CourseResponse updateCourse(long courseId, CourseUpdateRequest updatedCourseRequest) {

		// Use the courseRepository to find the Course entity by id.
		Course existingCourse = courseRepository.findById(courseId)
				.orElseThrow(() -> new EntityNotFoundException("Course not found with ID: " + courseId));
		//existingCourse.setName(updatedCourseRequest.getName());
		//existingCourse.setDescription(updatedCourseRequest.getDescription());
		 // Update the existing course entity with the data from the updatedCourseRequest
       modelMapper.map(updatedCourseRequest, existingCourse);
		
		Course updatedCourse = null;
		try {
			// Save the updated Course entity using the course repository.
			updatedCourse = courseRepository.save(existingCourse);
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
			throw new CustomException(ErrorHandlerMessage.SAVING_DB);
		}
		// Map the updated Course entity back to a Course dto
		return modelMapper.map(updatedCourse, CourseResponse.class);

	}

	/**
	 * Delete a Course by provided course id.
	 * 
	 * @param Student The id of the Course to be deleted.
	 * 
	 * @return The existing Course Dto
	 * 
	 * @throws EntityNotFoundException if the course with the given id is not found.
	 * 
	 * @throws CustomException         if there is an issue with deleting the
	 *                                 Course.
	 */
	@Override
	public boolean deleteCourseById(long courseId) {

		// Find the Course entity by course id
		Course existingCourse = courseRepository.findById(courseId)
				.orElseThrow(() -> new EntityNotFoundException("Course not found with ID: " + courseId));
		try {

			// Delete the Course entity using the course repository
			courseRepository.delete(existingCourse);
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}

		return true;
		// Map the existing course to course dto
		//return modelMapper.map(existingCourse, CourseResponse.class);

	}

	/**
	 * Retrieves a list of all courses from the course repository then map into list
	 * of CourseResponse.
	 * 
	 * @return List of Course Dtos representing all Courses.
	 * 
	 * @throws NotFoundException if the retrieved course list is empty.
	 */
	@Override
	public List<CourseResponse> getAllCourses() {
		// Use the courseRepository to fetch all Course entities
		List<Course> courses = courseRepository.findAll();
		if (courses.isEmpty()) {
			throw new NotFoundException("No Courses found");
		}
		// Map the list of course entities to a list of Course Dtos
		return courses.stream().map(course -> modelMapper.map(course, CourseResponse.class))
				.collect(Collectors.toList());
	}

}
