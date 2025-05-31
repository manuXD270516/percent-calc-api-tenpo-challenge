package com.tenpo.challenge.percentagecalcapi.service;

import com.tenpo.challenge.percentagecalcapi.model.CalculationRequest;
import com.tenpo.challenge.percentagecalcapi.model.CalculationResponse;
import com.tenpo.challenge.percentagecalcapi.service.impl.CalculationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CalculationServiceImplTest {

    private PercentageService percentageService;
    private CalculationServiceImpl calculationService;

    @BeforeEach
    void setUp() {
        percentageService = mock(PercentageService.class);
        calculationService = new CalculationServiceImpl(percentageService);
    }

    @Test
    void shouldCalculateCorrectlyWithPercentage() {
        // Arrange
        CalculationRequest request = new CalculationRequest(10.0, 5.0);
        when(percentageService.getPercentage()).thenReturn(20.0); // porcentaje 20%

        // Act
        CalculationResponse response = calculationService.calculateWithPercentage(request);

        // Assert
        double expectedSum = 15.0;
        double expectedResult = expectedSum + (expectedSum * 0.20); // 18.0

        assertEquals(20.0, response.getPercentageApplied());
        assertEquals(expectedResult, response.getResult());

        verify(percentageService).getPercentage();
    }
}
