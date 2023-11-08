package github.com.ldehner.microhub.service;

import github.com.ldehner.microhub.error.MessageNotFoundException;
import github.com.ldehner.microhub.model.Message;
import github.com.ldehner.microhub.repository.MessageRepository;

import java.rmi.server.UID;
import java.util.Map;

public class MessageService {
    private final MessageRepository messageRepository;
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    // Add a message to the repository
    public void addMessage(Message message) {
        messageRepository.addMessage(message);
    }

    public Message getMessage(UID uid) throws MessageNotFoundException {
        return messageRepository.getMessage(uid);
    }

    // Retrieve all messages from the repository
    public Map<UID, Message> getAllMessages() {
        return messageRepository.getAllMessages();
    }
}
