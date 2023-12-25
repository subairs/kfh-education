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
import com.kfh.education.exception.NotFoundException;
import com.kfh.education.exception.ServerSideException;
import com.kfh.education.repository.CourseRepository;
import com.kfh.education.request.CourseRequest;
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

	
	@Override
	public CourseResponse createCourse(CourseRequest courseRequest) {
		LOGGER.info("***** CourseService -> Create Course Started - " + new Date());

		Course course = modelMapper.map(courseRequest, Course.class);
		Course createdCourse;
		try {
			createdCourse = courseRepository.save(course);
		} catch (InternalServerError ex) {
			throw new ServerSideException(ex.getMessage());
		}
		catch (Exception ex) {
            // Catch any other unexpected exceptions
            throw new CustomException("An error occurred during course save: " + ex.getMessage());
        }

		LOGGER.info("***** CourseService -> Create Course End - " + new Date());

		return modelMapper.map(createdCourse, CourseResponse.class);

	}

	@Override
	public CourseResponse getCourseById(long courseId) {
		Course course = courseRepository.findById(courseId)

				.orElseThrow(() -> new EntityNotFoundException("Course not found with ID: " + courseId));

		return modelMapper.map(course, CourseResponse.class);
	}

	@Override
	public CourseResponse updateCourse(long courseId, CourseRequest courseRequest) {

		Course existingCourse = courseRepository.findById(courseId)
				.orElseThrow(() -> new EntityNotFoundException("Course not found with ID: " + courseId));
		existingCourse.setName(courseRequest.getName());
		existingCourse.setDescription(courseRequest.getDescription());
		Course updatedCourse = courseRepository.save(existingCourse);
		return modelMapper.map(updatedCourse, CourseResponse.class);

	}

	@Override
	public CourseResponse deleteCourseById(long courseId) {
		Course existingCourse = courseRepository.findById(courseId)
				.orElseThrow(() -> new EntityNotFoundException("Course not found with ID: " + courseId));

		courseRepository.delete(existingCourse);
		return modelMapper.map(existingCourse, CourseResponse.class);

	}

	/**
	 * Retrieves a list of all courses from the course repository then map into list
	 * of CourseResponse.
	 * 
	 */
	@Override
	public List<CourseResponse> getAllCourses() {
		List<Course> courses = courseRepository.findAll();
		if (courses.isEmpty()) {
			throw new NotFoundException("No Courses found");
		}
		return courses.stream().map(course -> modelMapper.map(course, CourseResponse.class))
				.collect(Collectors.toList());
	}

}
