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
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
public class MessageController {
    private static final Logger logger = LogManager.getLogger(MessageController.class);

    private final IMessageRepository messageRepository;
    private final SimpMessageSendingOperations messagingTemplate;

    // Constructor injection of the repository
    @Autowired
    public MessageController(@Qualifier("inMemoryMessageRepository") IMessageRepository messageRepository, SimpMessageSendingOperations messagingTemplate) {
        this.messageRepository = messageRepository;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload Message msg) {
        logger.info("New message: {}", msg.getContent());
        msg.setUid(UUID.randomUUID().toString());
        var newMsg = messageRepository.save(msg);
        logger.info("Saved message {}", newMsg.getUid());
        logger.info("Sending message to /topic/{}", newMsg.getType());
        messagingTemplate.convertAndSend("/topic/" + newMsg.getTopic(), newMsg);
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public Message addUser(@Payload Message msg, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", msg.getSender());
        logger.info("New message: {}", msg.getContent());
        msg.setUid(UUID.randomUUID().toString());
        return messageRepository.save(msg);
    }
}
