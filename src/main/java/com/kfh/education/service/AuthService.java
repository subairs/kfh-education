package com.kfh.education.service;

import java.util.Set;

public interface AuthService {
	
	void createUserWithRoles(String username, String password, Set<String> roleNames);

}
