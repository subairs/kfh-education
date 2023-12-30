package com.kfh.education.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kfh.education.entity.Role;
 
/**
 * 
 * @author subair
 * THis interface responsible for mapping Role entities in the database
 */

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByName(String name);
}
