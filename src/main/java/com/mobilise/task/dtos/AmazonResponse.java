package com.mobilise.task.dtos;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AmazonResponse {
    private String url;
    private String fileName;
}
