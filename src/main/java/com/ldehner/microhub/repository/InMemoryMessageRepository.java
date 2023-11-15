package com.ldehner.microhub.repository;

import com.ldehner.microhub.controller.MessageController;
import com.ldehner.microhub.model.Message;
import io.micrometer.common.lang.NonNullApi;
import lombok.NonNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

@Repository
@NonNullApi
public class InMemoryMessageRepository implements IMessageRepository {
    private static final Logger logger = LogManager.getLogger(InMemoryMessageRepository.class);
    private final ConcurrentNavigableMap<UUID, Message> messages = new ConcurrentSkipListMap<>();
    private final ConcurrentNavigableMap<Long, Message> sortedMessages = new ConcurrentSkipListMap<>();

    @Override
    public <S extends Message> S save(S entity) {
        messages.put(UUID.fromString(entity.getUid()), entity);
        sortedMessages.put(System.currentTimeMillis(), entity);
        logger.info("Saved message {}", entity.getUid());
        return entity;
    }

    @Override
    public <S extends Message> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Message> findById(UUID uid) {
        if(messages.containsKey(uid)){
            logger.info("Getting message {}", uid.toString());
            return Optional.of(messages.get(uid));
        }
        logger.error("Failed to get message by ID {}", uid.toString());
        return Optional.empty();
    }

    @Override
    public boolean existsById(UUID uid) {
        return false;
    }

    @Override
    public Iterable<Message> findAll() {
        logger.info("Getting all Messages");
        return sortedMessages.values();
    }

    @Override
    public Iterable<Message> findAllById(Iterable<UUID> uids) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(UUID uid) {

    }

    @Override
    public void delete(Message entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends UUID> uids) {

    }

    @Override
    public void deleteAll(Iterable<? extends Message> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
