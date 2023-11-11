package com.ldehner.microhub.model;

import lombok.*;

import java.rmi.server.UID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {
    private String content;
    private String sender;
    private MessageType type;
    private String uid;

}
