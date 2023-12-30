package com.kfh.education.serviceimpl;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kfh.education.entity.Role;
import com.kfh.education.entity.User;
import com.kfh.education.exception.CustomException;
import com.kfh.education.exception.ErrorHandlerMessage;
import com.kfh.education.repository.UserRepository;

/**
 * 
 * @author subair
 * 
 *         Service class representing for UserDetailsService
 *         {@link UserDetailsService}
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	// Autowired UserRepository for database operations
	@Autowired
	private UserRepository userRepository;

	// Logger for UserDetailsServiceImpl
	private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

	/**
	 * Retrieve UserDetails {@link UserDetails}, user details with role
	 *
	 * @param username The username of credentials.
	 * 
	 * @return {@link UserDetails}
	 * 
	 * @throws UsernameNotFoundException, if user not present in database.
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = null;
		try {
			user = userRepository.findByUsername(username);

			if (user == null) {
				throw new UsernameNotFoundException("User not found with username: " + username);
			}
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			throw new CustomException(ErrorHandlerMessage.RETRIEVING_DB);
		}

//		return org.springframework.security.core.userdetails.User.builder()
//				.username(user.getUsername())
//				.password(user.getPassword())
//				.roles("USER").build();
//		

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				getAuthorities(user.getRoles()));
	}

	/**
	 * Retrieve set of GrantedAuthority for roles.
	 * 
	 * @param roles The Set of role.
	 * @return Set of GrantedAuthority.
	 */
	private Set<GrantedAuthority> getAuthorities(Set<Role> roles) {
		Set<GrantedAuthority> authorities = new HashSet<>();
		for (Role role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		return authorities;
	}

}
