package com.ldehner.microhub.config;

import com.ldehner.microhub.model.Message;
import com.ldehner.microhub.model.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {
    private final SimpMessageSendingOperations msgTemp;
    @EventListener
    public void HandleWebSocketDisconnectListener(SessionDisconnectEvent e){
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(e.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if (username != null){
            log.info("User disconnected: {}", username);
            var msg = Message.builder()
                    .type(MessageType.LEAVE)
                    .sender(username)
                    .build();
            msgTemp.convertAndSend("/topic/public", msg);
        }
    }

    @EventListener
    public void HandleWebSocketConnectListener(SessionConnectedEvent e){
        log.info("User connected");
    }
}
