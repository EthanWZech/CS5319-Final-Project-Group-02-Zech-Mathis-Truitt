package com.pg2.loom.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @NoArgsConstructor
public class VoteRequest {
    private boolean vote;

    public VoteRequest(boolean vote) {
        this.vote = vote;
    }
}
