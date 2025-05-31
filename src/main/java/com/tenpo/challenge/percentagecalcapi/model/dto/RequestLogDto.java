package com.tenpo.challenge.percentagecalcapi.model.dto;

import com.tenpo.challenge.percentagecalcapi.model.EndpointType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class RequestLogDto {
    private Long id;
    private LocalDateTime timestamp;
    private String endpoint;
    private String parameters;
    private String response;
    private boolean success;
    private EndpointType endpointType;
}
