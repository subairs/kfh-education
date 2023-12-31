package com.kfh.education.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import com.kfh.education.entity.Course;
import com.kfh.education.repository.CourseRepository;
import com.kfh.education.request.CourseRequest;
import com.kfh.education.request.CourseUpdateRequest;
import com.kfh.education.response.CourseResponse;

class CourseServiceImplTest {

	// Create mock of CourseRepository
	@Mock
	CourseRepository courseRepository;

	// Create mock of ModelMapper
	@Mock
	ModelMapper modelMapper;

	@InjectMocks
	CourseServiceImpl courseServiceImpl;

	public CourseServiceImplTest() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetAllCourses() {

		// Mock the behavior of CourseRepository
		Course course1 = new Course(1, "CS", "Computer Science");
		Course course2 = new Course(2, "MBA", "Master in Business Administration");

		List<Course> courses = Arrays.asList(course1, course2);

		when(courseRepository.findAll()).thenReturn(courses);

		// Mock behavior of ModelMapper
		CourseResponse courseDTO1 = new CourseResponse(1, "CS", "Computer Science");
		CourseResponse courseDTO2 = new CourseResponse(2, "MBA", "Master in Business Administration");

		when(modelMapper.map(course1, CourseResponse.class)).thenReturn(courseDTO1);
		when(modelMapper.map(course2, CourseResponse.class)).thenReturn(courseDTO2);

		// Test CourseService method using the mocked repository and ModelMapper
		List<CourseResponse> courseDTOListResult = courseServiceImpl.getAllCourses();

		// Verify the result
		assertEquals(2, courseDTOListResult.size());
		assertEquals("CS", courseDTOListResult.get(0).getName());
		assertEquals("MBA", courseDTOListResult.get(1).getName());

	}

	@Test
	void testGetCourseById() {

		// Mock behavior of CourseRepository
		long courseId = 1l;
		Course course = new Course(courseId, "CS", "Computer Science");
		when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

		// Mock behavior of ModelMapper

		CourseResponse courseDTO = new CourseResponse(courseId, "CS", "Computer Science");
		when(modelMapper.map(course, CourseResponse.class)).thenReturn(courseDTO);

		// Test CourseServiceImpl method using the mock CourseRepository and
		// ModelMappper
		CourseResponse courseDTOResult = courseServiceImpl.getCourseById(courseId);

		// Verify the result
		assertEquals(courseDTO, courseDTOResult);

	}

	@Test
	void testCreateCourse() {
		// Mock behavior of ModelMapper
		CourseRequest courseRequestDTO = new CourseRequest("CS", "Computer Science");
        Course course = new Course(1, "CS", "Computer Science");
        Course savedCourse = new Course(1, "CS", "Computer Science");
        when(modelMapper.map(courseRequestDTO, Course.class)).thenReturn(course);
        when(courseRepository.save(course)).thenReturn(savedCourse);

        // Test CourseService method using the mocked repository and ModelMapper
        CourseResponse savedCourseDTO = new CourseResponse(1, "CS", "Computer Science");
        when(modelMapper.map(savedCourse, CourseResponse.class)).thenReturn(savedCourseDTO);

        CourseResponse result = courseServiceImpl.createCourse(courseRequestDTO);

        // Verify that repository.save() was called and return value
        verify(courseRepository).save(course);
        assertEquals(savedCourseDTO, result);
    
	}
	
    @Test
    public void testUpdateCourse() {
        // Mock behavior of CourseRepository
        Long courseId = 1L;
        CourseUpdateRequest updateRequestDTO = new CourseUpdateRequest(1, "CS", "Computer Science");
        Course existingCourse = new Course(1, "CS1", "Computer Science1");
        Course savedCourse = new Course(1, "CS", "Computer Science");
        
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(existingCourse));
        
        when(modelMapper.map(updateRequestDTO, Course.class)).thenReturn(existingCourse);
        when(courseRepository.save(existingCourse)).thenReturn(savedCourse);

        // Mock behavior of ModelMapper
        CourseResponse savedCourseResponseDTO = new CourseResponse(1, "CS", "Computer Science");
        when(modelMapper.map(savedCourse, CourseResponse.class)).thenReturn(savedCourseResponseDTO);

        // Test CourseService method using the mocked repository and ModelMapper
        CourseResponse result = courseServiceImpl.updateCourse(courseId, updateRequestDTO);

        // Verify the interactions and return value
        verify(courseRepository).findById(courseId);
        verify(courseRepository).save(existingCourse);
        assertEquals(savedCourseResponseDTO, result);
    }
    
    @Test
    public void testDeleteCourse() {
        // Mock behavior of CourseRepository
        Long courseId = 1L;
        Course courseToDelete = new Course(1, "CS", "Computer Science");
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(courseToDelete));

        // Test CourseService method using the mocked repository
        boolean result = courseServiceImpl.deleteCourseById(courseId);

        // Verify the delete operation
        verify(courseRepository).delete(courseToDelete);
        assertTrue(result);
    }
    
	

}
