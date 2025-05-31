package com.tenpo.challenge.percentagecalcapi.controller;

import com.tenpo.challenge.percentagecalcapi.model.EndpointType;
import com.tenpo.challenge.percentagecalcapi.model.RequestLog;
import com.tenpo.challenge.percentagecalcapi.repository.RequestLogRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class HistoryControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RequestLogRepository requestLogRepository;

    @InjectMocks
    private HistoryController historyController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(historyController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }


    @Test
    void shouldReturnPaginatedRequestLogHistory() throws Exception {
        RequestLog log = new RequestLog();
        log.setId(1L);
        log.setTimestamp(LocalDateTime.of(2025, 5, 31, 10, 0));
        log.setEndpoint("/api/v1/calculate");
        log.setParameters("CalculationRequest(num1=100.0,num2=50.0)");
        log.setResponse("CalculationResponse(result=160.5, percentageApplied=7.0)");
        log.setSuccess(true);
        log.setEndpointType(EndpointType.CALCULATION);

        Pageable pageable = PageRequest.of(0, 10, Sort.by("timestamp").descending());
        Page<RequestLog> page = new PageImpl<>(Collections.singletonList(log), pageable, 1);

        when(requestLogRepository.findAll(pageable)).thenReturn(page);

        mockMvc.perform(get("/api/v1/history")
                        .param("page", "0")
                        .param("size", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").isArray())
                .andExpect(jsonPath("$.items[0].id").value(1))
                .andExpect(jsonPath("$.items[0].endpoint").value("/api/v1/calculate"))
                .andExpect(jsonPath("$.items[0].parameters").value("CalculationRequest(num1=100.0,num2=50.0)"))
                .andExpect(jsonPath("$.items[0].response").value("CalculationResponse(result=160.5, percentageApplied=7.0)"))
                .andExpect(jsonPath("$.items[0].success").value(true))
                .andExpect(jsonPath("$.items[0].endpointType").value("CALCULATION"))
                .andExpect(jsonPath("$.total").value(1))
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.size").value(10));
    }
}
