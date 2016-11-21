package com.asapp.rest.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.asapp.rest.model.Message;

@RestController
@RequestMapping("/message")
public class MessageApi {

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addMessage(@RequestBody Message message) {
		
		return null;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public Message getMessage(@RequestParam("user1") String user1, 
							  @RequestParam("user2") String user2,
							  @RequestParam("limit") String limit,
							  @RequestParam("page") String page) {
		return null;
	}
	
}
