package com.asapp.rest.api;

import com.asapp.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
public class UserApi {
	
	@Autowired
	UserService userService;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addUser(@RequestBody User user) {
		
		ResponseEntity<Void> responseEntity = userService.addNewUser(user);
		
		return responseEntity;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Response> authenticateUser(@RequestParam("name") String name, @RequestParam("pass") String pass ) {

		Response response = new Response();
		response.setMessage("API Not Ready");
		response.setStatus("400");
		response.setResponse("API Not Ready");

		ResponseEntity<Response> responseEntity = new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		
		return responseEntity;
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<?> changePassword() {
		
		ResponseEntity<Void> responseEntity = ResponseEntity.notFound().build();
		
		return responseEntity;
	}
}
