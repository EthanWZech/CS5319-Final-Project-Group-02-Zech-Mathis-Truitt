package com.pg2.loom.websockets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pg2.loom.dto.AddCommentRequest;
import com.pg2.loom.dto.AddThreadRequest;
import com.pg2.loom.dto.HomeThreadsDto;
import com.pg2.loom.dto.ThreadWithCommentsDto;
import com.pg2.loom.message.TestMessage;
import com.pg2.loom.service.CommentService;
import com.pg2.loom.service.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
public class WebSocketHomeHandler extends TextWebSocketHandler {
    private static final CopyOnWriteArraySet<WebSocketSession> sessions = new CopyOnWriteArraySet<>();
    private final ObjectMapper mapper = new ObjectMapper();
    private final ThreadService threadService;

    @Autowired
    public WebSocketHomeHandler(ThreadService threadService, CommentService commentService) {
        this.threadService = threadService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);

        sendTopThreads();
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        JsonNode root = mapper.readTree(message.getPayload());
        String type = root.get("type").asText();
        JsonNode payload = root.get("payload");

        switch (type) {
            case "addThread":
                AddThreadRequest request = mapper.readValue(payload.toString(), AddThreadRequest.class);

                threadService.createThread(request);

                sendTopThreads();
                break;
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
    }

    private void sendTopThreads() throws Exception {
        HomeThreadsDto topThreads = new HomeThreadsDto(threadService.getMostRecentThreads());

        for(WebSocketSession session : sessions) {
            session.sendMessage(new TextMessage(mapper.writeValueAsString(topThreads)));
        }
    }
}
