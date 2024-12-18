package com.ldehner.microhub.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {
    private String content;
    private String sender;
    private MessageType type;
    private String topic;
    private String uid;
}
