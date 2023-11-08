package github.com.ldehner.microhub.repository;


import github.com.ldehner.microhub.error.MessageNotFoundException;
import github.com.ldehner.microhub.model.Message;

import java.rmi.server.UID;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public interface MessageRepository {
    /**
     * Adds a new message to the repository
     * @param message the message to be stored
     */
    void addMessage(Message message);

    /**
     * Gets the message of a specific uid
     * @param uid the id of the message
     * @return the message
     */
    Message getMessage(UID uid) throws MessageNotFoundException;

    /**
     * Returns all messages
     * @return all the messages
     */
    Map<UID, Message> getAllMessages();
}
