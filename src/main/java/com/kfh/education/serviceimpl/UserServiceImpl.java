package com.kfh.education.serviceimpl;

import java.util.HashSet;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kfh.education.entity.Role;
import com.kfh.education.entity.User;
import com.kfh.education.repository.RoleRepository;
import com.kfh.education.repository.UserRepository;
import com.kfh.education.service.UserService;
/**
 * 
 * @author subair
 * Service class representing business logic for User
 */

@Service
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	public UserServiceImpl(UserRepository userRepository,
			RoleRepository roleRepository,
			PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}
	

	/**
	 * Create user with specified role
	 * @param username Username of the User
	 * @param password Password of the User
	 * @param roleName Role name of the User
	 */
	@Override
	public void createUserWithRole(String username, String password, String roleName) {
		
		User user = new User();
		user.setPassword(passwordEncoder.encode(password));
		user.setUsername(username);
		Role role = roleRepository.findByName(roleName);
		
		if(role == null) {
			role =new Role();
			role.setName(roleName);
			roleRepository.save(role);
		}
		user.setRoles(new HashSet<>());
		user.getRoles().add(role);
		userRepository.save(user);
		
	}
	
	
}
