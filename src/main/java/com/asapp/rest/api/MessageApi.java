package com.asapp.rest.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.asapp.rest.model.Message;
import com.asapp.rest.model.Response;
import com.asapp.service.MessageService;

@RestController
@RequestMapping("/message")
public class MessageApi {
	
	@Autowired
	MessageService messageService;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Response> addMessage(@RequestBody Message message) {
		
		ResponseEntity<Response> responseEntity = messageService.insertMessage(message);
		
		return responseEntity;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public List<Message> getMessage(@RequestParam("sender") String sender, 
							  @RequestParam("receiver") String receiver,
							  @RequestParam("limit") String limit,
							  @RequestParam("page") String page) {
		
		List<Message> messages = messageService.getMessages(sender, receiver, limit, page);
		
		return messages;
	}
	
}
