package com.asapp.service;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.asapp.dao.MessageDAO;
import com.asapp.rest.model.Message;
import com.asapp.rest.model.Response;

@Service
public class MessageService {

	@Autowired
	MessageDAO messageDAO;

	public ResponseEntity<Response> insertMessage(Message message) {
		
		if(message.getMessageType().equals("image")) {
			try {
				URL url = new URL(message.getMessageBody());
				BufferedImage image = ImageIO.read(url);
				int height = image.getHeight();
				int width = image.getWidth();
				
				StringBuffer metaData = new StringBuffer();
				metaData.append("height : " + height);
				metaData.append(" width : " + width);
				
				message.setMetaData(metaData.toString());
				
			} catch(Exception e) {
				Response responseBody = new Response();
				responseBody.setMessage("Only supporting image, video and txt at this time");
				responseBody.setStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE.toString());
				responseBody.setResponse("Unsupported Media Type");
				
				ResponseEntity<Response> response = new ResponseEntity(responseBody, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
				
				return response;
			}
		} else if(message.getMessageType().equals("video")) {
			
			StringBuffer metaData = new StringBuffer();
			metaData.append("source : youtube");
			metaData.append(" duration : 00:00:00");
			
			message.setMetaData(metaData.toString());
			
		} else if(message.getMessageType().equals("txt")) {
			
		} else {
			Response responseBody = new Response();
			responseBody.setMessage("Only supporting image, video and txt at this time");
			responseBody.setStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE.toString());
			responseBody.setResponse("Unsupported Media Type");
			
			ResponseEntity<Response> response = new ResponseEntity(responseBody, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
			
			return response;
		}

		// if insert successful - send 200
		boolean dbResponse = messageDAO.insertMessage(message);

		if (dbResponse) {
			Response responseBody = new Response();
			responseBody.setMessage("Message Posted");
			responseBody.setStatus(HttpStatus.OK.toString());
			responseBody.setResponse("Message Posted");
			
			ResponseEntity<Response> response = new ResponseEntity(responseBody, HttpStatus.OK);
			
			return response;
		}

		Response responseBody = new Response();
		responseBody.setMessage("Message not posted");
		responseBody.setStatus(HttpStatus.BAD_REQUEST.toString());
		responseBody.setResponse("Bad Request");
		
		ResponseEntity<Response> response = new ResponseEntity(responseBody, HttpStatus.BAD_REQUEST);
		
		return response;
	}

	public List<Message> getMessages(String sender, String receiver, String limit, String page) {

		// collect metadata as well

		// if insert successful - send 200
		List<Message> response = messageDAO.getMessages(sender, receiver, limit, page);

		if (response != null) {
			return response;
		}

		return response;
	}

}
