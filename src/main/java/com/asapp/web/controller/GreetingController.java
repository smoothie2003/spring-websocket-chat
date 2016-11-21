package com.asapp.web.controller;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.asapp.rest.model.Greet;
import com.asapp.rest.model.HelloMessage;

@Controller
public class GreetingController {


    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greet greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greet("Hello, " + message.getName() + "!");
    }

}