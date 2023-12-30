package com.kfh.education.serviceimpl;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;

import com.kfh.education.entity.Role;
import com.kfh.education.entity.User;
import com.kfh.education.exception.CustomException;
import com.kfh.education.exception.ErrorHandlerMessage;
import com.kfh.education.exception.RoleNotFoundException;
import com.kfh.education.exception.ServerSideException;
import com.kfh.education.repository.RoleRepository;
import com.kfh.education.repository.UserRepository;
import com.kfh.education.service.AuthService;

import jakarta.persistence.EntityNotFoundException;

/**
 * 
 * @author subair Service class representing business logic for User
 */

@Service
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;

	public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
			PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

	/**
	 * Create user with specified role
	 * 
	 * @param username  Username of the User
	 * @param password  Password of the User
	 * @param roleNames Set<Role> of the User, eg: ["USER","ADMIN"}
	 */
	@Transactional
	@Override
	public void createUserWithRoles(String username, String password, Set<String> roleNames) {

		User user = new User();
		user.setPassword(passwordEncoder.encode(password));
		user.setUsername(username);
		try {
			Set<Role> roles = new HashSet<>();
			for (String roleName : roleNames) {
				Role role = roleRepository.findByName(roleName);
				if (role == null) {
					
					throw new RoleNotFoundException("Role not found: " + roleName);
				}
				roles.add(role);
			}
			user.setRoles(roles);
		} 
		
		catch (Exception ex) {
			// Catch any other unexpected exceptions
			LOGGER.error(ex.getMessage());
			throw new CustomException(ErrorHandlerMessage.RETRIEVING_DB);
		}

		try {
			userRepository.save(user);
		} catch (DataIntegrityViolationException ex) {
			LOGGER.error(ex.getMessage());
			throw new DataIntegrityViolationException("Constraint violation error occurred.");

		} catch (InternalServerError ex) {
			LOGGER.error(ex.getMessage());
			throw new ServerSideException(ex.getMessage());
		} catch (Exception ex) {
			// Catch any other unexpected exceptions
			LOGGER.error(ex.getMessage());
			throw new CustomException(ErrorHandlerMessage.SAVING_DB);
		}

	}

}
