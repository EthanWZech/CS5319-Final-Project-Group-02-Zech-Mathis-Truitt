package com.pg2.loom.dto;

import com.pg2.loom.entity.Thread;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class HomeThreadsDto implements TypedDto {
    private final String type = "HomeThreadsDto";

    private List<ThreadDto> threads = new ArrayList<>();

    public HomeThreadsDto(List<ThreadDto> threads) {
        this.threads = threads;
    }
}
