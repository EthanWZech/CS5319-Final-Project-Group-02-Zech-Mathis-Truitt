package com.pg2.loom.websockets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pg2.loom.dto.AddCommentRequest;
import com.pg2.loom.dto.ThreadWithCommentsDto;
import com.pg2.loom.entity.Comment;
import com.pg2.loom.entity.Thread;
import com.pg2.loom.message.TestMessage;
import com.pg2.loom.service.CommentService;
import com.pg2.loom.service.ThreadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
public class WebSocketThreadHandler extends TextWebSocketHandler {
    private static final ConcurrentHashMap<String, CopyOnWriteArraySet<WebSocketSession>> threads = new ConcurrentHashMap<>();
    private final ObjectMapper mapper = new ObjectMapper();
    private final ThreadService threadService;
    private final CommentService commentService;

    @Autowired
    public WebSocketThreadHandler(ThreadService threadService, CommentService commentService) {
        this.threadService = threadService;
        this.commentService = commentService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String threadId = extractThreadId(session);
        threads.putIfAbsent(threadId, new CopyOnWriteArraySet<>());
        threads.get(threadId).add(session);

        sendThread(threadId);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        JsonNode root = mapper.readTree(message.getPayload());
        String threadId = extractThreadId(session);
        String type = root.get("type").asText();
        JsonNode payload = root.get("payload");

        switch (type) {
            case "addComment":
                AddCommentRequest request = mapper.readValue(payload.toString(), AddCommentRequest.class);

                if(request.getParentCommentId() == null) {
                    commentService.addCommentToThread(request);
                }
                else{
                    commentService.addReplyToComment(request);
                }

                sendThread(threadId);
                break;
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

    private void sendThread(String threadId) throws Exception {
        ThreadWithCommentsDto threadTree = threadService.getThreadAsTree(Long.valueOf(threadId));

        for(WebSocketSession session : threads.getOrDefault(threadId, new CopyOnWriteArraySet<>())) {
            session.sendMessage(new TextMessage(mapper.writeValueAsString(threadTree)));
        }
    }
}
