package com.kfh.education.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kfh.education.config.security.JwtService;
import com.kfh.education.exception.ErrorHandlerMessage;
import com.kfh.education.request.AuthRequestDTO;
import com.kfh.education.request.RegistrationRequest;
import com.kfh.education.response.JwtResponseDTO;
import com.kfh.education.service.AuthService;
import com.kfh.education.serviceimpl.UserDetailsServiceImpl;
import com.kfh.education.serviceimpl.AuthServiceImpl;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("user")
//@SecurityRequirement(name = "kfh-education")
@Validated
public class AuthController {
	
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final AuthService authService;
	private final UserDetailsServiceImpl userDetailsServiceImpl;
	
	public AuthController(AuthenticationManager authenticationManager,
			JwtService jwtService,
			AuthServiceImpl userService,UserDetailsServiceImpl userDetailsServiceImpl) {

		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
		this.authService = userService;
		this.userDetailsServiceImpl = userDetailsServiceImpl;
	}
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
	
    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody @Valid RegistrationRequest request) {
    	authService.createUserWithRoles(request.getUsername(), request.getPassword(), request.getRoles());
        return ResponseEntity.ok("User created successfully");
    }

	@PostMapping("/login")
	public JwtResponseDTO AuthenticateAndGetToken(@RequestBody @Valid AuthRequestDTO authRequestDTO){
		try {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
	    		authRequestDTO.getUsername(),
	    		authRequestDTO.getPassword()));
	    
	    if(authentication.isAuthenticated()){
	    	String accessToken=jwtService.generateToken(authRequestDTO.getUsername());
	    	JwtResponseDTO jwtResponseDTO = new JwtResponseDTO();
	    	jwtResponseDTO.setAccessToken(accessToken);
	    	return jwtResponseDTO;
	    	
	    } else {
	        throw new UsernameNotFoundException("invalid user request..!!");
	    }
		}catch(BadCredentialsException ex) {
			LOGGER.error(ex.getMessage());
			throw new BadCredentialsException(ErrorHandlerMessage.BAD_CREDENTIAL);
		}
	}

}
