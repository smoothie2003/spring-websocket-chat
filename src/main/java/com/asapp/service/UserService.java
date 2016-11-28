package com.asapp.service;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.asapp.dao.UserDAO;
import com.asapp.rest.model.Response;
import com.asapp.rest.model.User;

@Service
public class UserService {

	@Autowired
	UserDAO userDAO;
	
	@Autowired
	EncryptionService encryptionService;

	public ResponseEntity<Response> addNewUser(User user) {

		ResponseEntity<Response> responseEntity = null;

		responseEntity = responseIfPassOrUserNotPopulated(user);

		if(responseEntity == null) {
			responseEntity = responseIfUserAlreadyExists(user);
		}
		
		//create the user
		if(responseEntity == null) {
			responseEntity = responseToCreateTheUser(user);
		}
		
		return responseEntity;
	}

	public ResponseEntity<Response> authenticateUser(User user) {

		ResponseEntity<Response> responseEntity = null;

		// Check if user already exists
		responseEntity = this.responseIfPassOrUserNotPopulated(user);

		if(responseEntity == null) {
			
			try {
				String decryptedPass = encryptionService.decrypt(user.getPass());
				
				User queriedUser = userDAO.findUser(user);
				
				String decryptedPassFromDB = encryptionService.decrypt(queriedUser.getPass());
				
				if(user.getUser().equals(queriedUser.getUser()) && decryptedPass.equals(decryptedPassFromDB)) {
					Response response = new Response();
					response.setStatus(HttpStatus.OK.toString());
					response.setMessage("User Authorized");
					response.setResponse("OK");
					responseEntity = new ResponseEntity(response, HttpStatus.OK); 
				} else {
					Response response = new Response();
					response.setStatus(HttpStatus.UNAUTHORIZED.toString());
					response.setMessage("User not authorized");
					response.setResponse("Unauthorized");
					responseEntity = new ResponseEntity(response, HttpStatus.UNAUTHORIZED);
				}
			} catch (Exception e) {
				e.printStackTrace();
				
				Response response = new Response();
				response.setStatus(HttpStatus.UNAUTHORIZED.toString());
				response.setMessage("User not authorized");
				response.setResponse("Unauthorized");
				responseEntity = new ResponseEntity(response, HttpStatus.UNAUTHORIZED);
			}
		}		
		return responseEntity;
	}

	private ResponseEntity<Response> responseToCreateTheUser(User user) {
		
		ResponseEntity<Response> responseBody = null;
		
		String encryptedPassword = null;
		
		try {
			encryptedPassword = encryptionService.encrypt(user.getPass());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		user.setPass(encryptedPassword);
		
		boolean userNameCreated = userDAO.createUserName(user);
		
		if(userNameCreated) {
			Response response = new Response();
			response.setStatus(HttpStatus.CREATED.toString());
			response.setMessage("User :" + user.getUser() + " created.");
			response.setResponse("User created");
			
			responseBody = new ResponseEntity(response, HttpStatus.CREATED);
			
			return responseBody;
		}
		
		Response response = new Response();
		response.setStatus(HttpStatus.SERVICE_UNAVAILABLE.toString());
		response.setMessage("User not created, service unavailable");
		response.setResponse("Service Unavailable");
		
		responseBody = new ResponseEntity(response, HttpStatus.SERVICE_UNAVAILABLE);
		
		return responseBody;
	}


	private ResponseEntity<Response> responseIfPassOrUserNotPopulated(User user) {

		if ((user.getPass() == null || user.getPass().isEmpty()) ||
			(user.getUser() == null || user.getUser().isEmpty())) {
			
			Response response = new Response();
			response.setStatus(HttpStatus.BAD_REQUEST.toString());
			response.setMessage("Password Or User not Populated");
			response.setResponse("Bad Request");
			
			ResponseEntity<Response> responseBody = new ResponseEntity(response, HttpStatus.BAD_REQUEST);
			
			return responseBody;
		}

		return null;
	}

	private ResponseEntity<Response> responseIfUserAlreadyExists(User user) {
		
		if(userDAO.isExistingUserName(user) == true) {
			
			Response response = new Response();
			response.setStatus(HttpStatus.UNAUTHORIZED.toString());
			response.setMessage("User already Existing");
			response.setResponse("Unauthorized");
			
			ResponseEntity<Response> responseBody = new ResponseEntity(response, HttpStatus.UNAUTHORIZED);
			
			return responseBody;
		}
		
		return null;
	}

}
