package com.pg2.loom.message;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TestMessage {
    @JsonProperty("username")
    private String username;

    @JsonProperty("message")
    private String message;

    public TestMessage() {}

    public TestMessage(String username, String message) {
        this.username = username;
        this.message = message;
    }

    public String getUsername() { return username; }
    public String getMessage() { return message; }
}
