package com.tenpo.challenge.percentagecalcapi.controller;

import com.tenpo.challenge.percentagecalcapi.async.HistoryLogger;
import com.tenpo.challenge.percentagecalcapi.model.CalculationRequest;
import com.tenpo.challenge.percentagecalcapi.model.CalculationResponse;
import com.tenpo.challenge.percentagecalcapi.service.CalculationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CalculationControllerTest {

    @Mock
    private CalculationService calculationService;

    @Mock
    private HistoryLogger historyLogger;

    @InjectMocks
    private CalculationController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnCalculatedResponse() {
        // Arrange
        CalculationRequest request = new CalculationRequest(100.0, 50.0);
        CalculationResponse expectedResponse = new CalculationResponse(160.5, 7.0);

        when(calculationService.calculateWithPercentage(request)).thenReturn(expectedResponse);

        // Act
        ResponseEntity<CalculationResponse> response = controller.calculate(request);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedResponse, response.getBody());
        verify(calculationService).calculateWithPercentage(request);
        verify(historyLogger).log(
                eq("/api/v1/calculate"),
                eq(request.toString()),
                eq(expectedResponse.toString()),
                eq(true),
                any()
        );
    }
}
