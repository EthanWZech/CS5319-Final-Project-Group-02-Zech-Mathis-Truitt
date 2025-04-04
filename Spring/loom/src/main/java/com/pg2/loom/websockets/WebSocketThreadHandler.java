package com.pg2.loom.websockets;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class WebSocketThreadHandler extends TextWebSocketHandler {
    private static final ConcurrentHashMap<String, CopyOnWriteArraySet<WebSocketSession>> threads = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String threadId = extractThreadId(session);
        threads.putIfAbsent(threadId, new CopyOnWriteArraySet<>());
        threads.get(threadId).add(session);

        session.sendMessage(new TextMessage("Connected to thread: " + threadId));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String threadId = extractThreadId(session);

        for (WebSocketSession client : threads.getOrDefault(threadId, new CopyOnWriteArraySet<>())) {
            if(client.isOpen()) {
                client.sendMessage(new TextMessage(message.getPayload()));
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String threadId = extractThreadId(session);
        threads.getOrDefault(threadId, new CopyOnWriteArraySet<>()).remove(session);
    }

    private String extractThreadId(WebSocketSession session) {
        String path = session.getUri().getPath();
        return path.substring(path.lastIndexOf('/') + 1);
    }
}
