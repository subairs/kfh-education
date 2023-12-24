package com.kfh.subair.studentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kfh.subair.studentservice.entity.Student;

/**
 * 
 * @author subair
 * Repository interface representing student
 * @see JpaRepository
 * @see Student
 */

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

}
