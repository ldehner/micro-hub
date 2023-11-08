package github.com.ldehner.microhub.repository;

import github.com.ldehner.microhub.error.MessageNotFoundException;
import github.com.ldehner.microhub.model.Message;

import java.rmi.server.UID;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class InMemoryMessageRepository implements MessageRepository{

    private final Map<UID, Message> messages = new ConcurrentHashMap<>();

    /**
     * Adds a new message to the repository
     *
     * @param message the message to be stored
     */
    @Override
    public void addMessage(Message message) {
        messages.put(message.getUid(), message);
    }

    /**
     * Gets the message of a specific uid
     *
     * @param uid the id of the message
     * @return the message
     */
    @Override
    public Message getMessage(UID uid) throws MessageNotFoundException {
        return messages.containsKey(uid) ? messages.get(uid) : throwException(String.format("Message not found with ID: %s", uid));
    }

    /**
     * Returns all messages
     *
     * @return all the messages
     */
    @Override
    public Map<UID, Message> getAllMessages() {
        return messages;
    }

    private Message throwException(String message) throws MessageNotFoundException {
        throw new MessageNotFoundException(message);
    }
}
