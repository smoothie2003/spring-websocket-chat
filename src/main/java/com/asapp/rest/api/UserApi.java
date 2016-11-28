package com.asapp.rest.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.asapp.rest.model.Response;
import com.asapp.rest.model.User;
import com.asapp.service.UserService;

@RestController
@RequestMapping("/user")
public class UserApi {
	
	@Autowired
	UserService userService;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Response> addUser(@RequestBody User user) {
		
		ResponseEntity<Response> responseEntity = userService.addNewUser(user);
		
		return responseEntity;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Response> authenticateUser(@RequestParam("user") String name, @RequestParam("pass") String pass ) {

		User user = new User(name, pass);

		ResponseEntity<Response> response = userService.authenticateUser(user);
		
		return response;
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<Response> changePassword(@RequestParam("user") String name, 
											@RequestParam("passOld") String passOld, 
											@RequestParam("passNew") String passNew) {
		
		Response responseBody = new Response();
		responseBody.setMessage("API not implemented");
		responseBody.setStatus(HttpStatus.NOT_IMPLEMENTED.toString());
		responseBody.setResponse("API not implemented");
		
		ResponseEntity<Response> responseEntity = new ResponseEntity(responseBody, HttpStatus.NOT_IMPLEMENTED); 
		
		return responseEntity;
	}
}
