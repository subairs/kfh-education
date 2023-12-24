package com.kfh.subair.studentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kfh.subair.studentservice.entity.Course;
/**
 * 
 * @author subair
 * Repository interface representing for course
 * @see JpaRepository
 * @see Course
 */

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

}
