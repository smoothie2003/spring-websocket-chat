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

@Service
public class MessageService {

	@Autowired
	MessageDAO messageDAO;

	public ResponseEntity<Void> insertMessage(Message message) {
		
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
				return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).build();
			}
		} else if(message.getMessageType().equals("video")) {
			
			StringBuffer metaData = new StringBuffer();
			metaData.append("source : youtube");
			metaData.append(" duration : 00:00:00");
			
			message.setMetaData(metaData.toString());
			
		} else if(message.getMessageType().equals("txt")) {
			
		} else {
			return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).build();
		}

		// if insert successful - send 200
		boolean response = messageDAO.insertMessage(message);

		if (response) {
			return ResponseEntity.status(HttpStatus.OK).build();
		}

		return ResponseEntity.badRequest().build();
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
