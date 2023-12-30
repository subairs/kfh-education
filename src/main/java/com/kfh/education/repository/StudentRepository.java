package com.kfh.education.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kfh.education.entity.Student;

/**
 * 
 * @author subair
 * Repository interface representing student
 * @see JpaRepository
 * @see Student
 */

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

	List<Student> findAllByCourseId(Long courseId);

}
