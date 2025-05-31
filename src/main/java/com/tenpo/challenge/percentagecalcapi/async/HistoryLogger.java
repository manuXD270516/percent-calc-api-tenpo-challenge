package com.tenpo.challenge.percentagecalcapi.async;


import com.tenpo.challenge.percentagecalcapi.model.EndpointType;
import com.tenpo.challenge.percentagecalcapi.model.RequestLog;
import com.tenpo.challenge.percentagecalcapi.repository.RequestLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class HistoryLogger {

    private final RequestLogRepository repository;

    @Async
    public void log(String endpoint, String parameters, String response, boolean success, EndpointType type) {
        try {
            RequestLog logEntry = RequestLog.builder()
                    .timestamp(LocalDateTime.now())
                    .endpoint(endpoint)
                    .endpointType(type)
                    .parameters(parameters)
                    .response(response)
                    .success(success)
                    .build();

            repository.save(logEntry);
        } catch (Exception e) {
            log.error("Error registrando historial", e);
        }
    }
}
