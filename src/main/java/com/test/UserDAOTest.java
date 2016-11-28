package com.test;

import com.asapp.dao.UserDAO;
import com.asapp.rest.model.User;

public class UserDAOTest {

	public static void main(User[] args) {
		UserDAO userDAO = new UserDAO();
		
		User findUser = userDAO.findUser(new User("VARUN", "Null"));
		
		System.out.println(findUser.getPass());

	}

}
