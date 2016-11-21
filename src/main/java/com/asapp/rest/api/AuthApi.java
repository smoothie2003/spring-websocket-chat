package com.asapp.rest.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.asapp.rest.model.User;
import com.asapp.service.UserService;

@RestController
@RequestMapping("/user")
public class AuthApi {
	
	@Autowired
	UserService userService;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addUser(@RequestBody User user) {
		
		ResponseEntity<Void> responseEntity = userService.addNewUser(user);
		
		return responseEntity;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> authenticateUser(@RequestParam("name") String name, @RequestParam("pass") String pass ) {
		
		ResponseEntity<Void> responseEntity = ResponseEntity.badRequest().build();
		
		return responseEntity;
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<?> changePassword() {
		
		ResponseEntity<Void> responseEntity = ResponseEntity.notFound().build();
		
		return responseEntity;
	}
}
