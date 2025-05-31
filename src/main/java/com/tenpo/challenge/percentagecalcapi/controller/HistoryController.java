package com.tenpo.challenge.percentagecalcapi.controller;

import com.tenpo.challenge.percentagecalcapi.model.RequestLog;
import com.tenpo.challenge.percentagecalcapi.model.dto.RequestLogDto;
import com.tenpo.challenge.percentagecalcapi.repository.RequestLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/history")
@RequiredArgsConstructor
public class HistoryController {

    private final RequestLogRepository requestLogRepository;

    @GetMapping
    public Map<String, Object> getHistory(@PageableDefault(sort = "timestamp", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<RequestLog> page = requestLogRepository.findAll(pageable);
        List<RequestLogDto> content = page.stream().map(this::toDto).toList();

        return Map.of(
                "items", content,
                "total", page.getTotalElements(),
                "page", page.getNumber(),
                "size", page.getSize()
        );
    }

    private RequestLogDto toDto(RequestLog log) {
        return RequestLogDto.builder()
                .id(log.getId())
                .timestamp(log.getTimestamp())
                .endpoint(log.getEndpoint())
                .parameters(log.getParameters())
                .response(log.getResponse())
                .success(log.isSuccess())
                .endpointType(log.getEndpointType())
                .build();
    }
}
