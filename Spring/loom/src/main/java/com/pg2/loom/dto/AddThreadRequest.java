package com.pg2.loom.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
public class AddThreadRequest {
    private String topic;

    private String username;

    private String title;

    private String text;

    private String image;

    public AddThreadRequest(String topic, String username, String title, String text, String image) {
        this.topic = topic;
        this.username = username;
        this.title = title;
        this.text = text;
        this.image = image;
    }
}
