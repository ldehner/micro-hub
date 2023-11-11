package com.ldehner.microhub.repository;

import com.ldehner.microhub.model.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IMessageRepository extends CrudRepository<Message, UUID> {
}
