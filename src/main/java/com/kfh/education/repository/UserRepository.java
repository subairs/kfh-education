package com.kfh.education.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kfh.education.entity.User;

/**
 * 
 * @author subair
 * Interface responsible for mapping User entities in the database.
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByUsername(String username);
	
}
