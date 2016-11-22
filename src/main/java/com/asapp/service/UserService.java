package com.asapp.service;

import com.asapp.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.asapp.dao.UserDAO;
import com.asapp.rest.model.User;

@Service
public class UserService {

	@Autowired
	UserDAO userDAO;

	public ResponseEntity<Void> addNewUser(User user) {

		ResponseEntity<Void> responseEntity = null;

		responseEntity = responseIfPassNotExist(user);

		if(responseEntity == null) {
			responseEntity = responseIfUserAlreadyExists(user);
		}
		
		//create the user
		if(responseEntity == null) {
			responseEntity = responseToCreateTheUser(user);
		}
		
		return responseEntity;
	}

	public Response authenticateUser(User user) {

		ResponseEntity<Void> responseEntity = null;

		// Check if user already exists
		responseEntity = ResponseEntity.badRequest().build();

		// If not success
		responseEntity = ResponseEntity.status(HttpStatus.OK).build();

		return null;
	}

	private ResponseEntity<Void> responseToCreateTheUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}


	private ResponseEntity<Void> responseIfPassNotExist(User user) {

		if (user.getPass() == null || user.getPass().isEmpty()) {
			return ResponseEntity.badRequest().build();
		}

		return null;
	}

	private ResponseEntity<Void> responseIfUserAlreadyExists(User user) {
		
		if(userDAO.isExistingUserName(user) == true) {
			return ResponseEntity.badRequest().build();
		}
		
		return null;
	}

}
