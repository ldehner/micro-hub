package com.ldehner.microhub.controller;

import com.ldehner.microhub.model.Message;
import com.ldehner.microhub.repository.IMessageRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class MessageController {
    private static final Logger logger = LogManager.getLogger(MessageController.class);

    private final IMessageRepository messageRepository;

    // Constructor injection of the repository
    @Autowired
    public MessageController(@Qualifier("inMemoryMessageRepository") IMessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public Message sendMessage(@Payload Message msg){
        logger.info("New message: {}",msg.getContent());
        msg.setUid(UUID.randomUUID().toString());
        return  messageRepository.save(msg);
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public Message addUser(@Payload Message msg, SimpMessageHeaderAccessor headerAccessor){
        headerAccessor.getSessionAttributes().put("username", msg.getSender());
        logger.info("New message: {}",msg.getContent());
        msg.setUid(UUID.randomUUID().toString());
        return messageRepository.save(msg);
    }
}
