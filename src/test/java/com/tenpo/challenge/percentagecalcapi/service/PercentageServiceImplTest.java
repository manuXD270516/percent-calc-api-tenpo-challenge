package com.tenpo.challenge.percentagecalcapi.service;

import com.tenpo.challenge.percentagecalcapi.cache.PercentageCache;
import com.tenpo.challenge.percentagecalcapi.external.PercentageApiClient;
import com.tenpo.challenge.percentagecalcapi.service.impl.PercentageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class PercentageServiceImplTest {

    @Mock
    private PercentageApiClient apiClient;

    @Mock
    private PercentageCache percentageCache;

    @InjectMocks
    private PercentageServiceImpl percentageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnPercentageFromApiAndSaveToCache() {
        // Arrange
        when(apiClient.getPercentageFromMock()).thenReturn(15.0);

        // Act
        double result = percentageService.getPercentage();

        // Assert
        assertEquals(15.0, result);
        verify(percentageCache).save(15.0);
    }

    @Test
    void shouldReturnPercentageFromCacheWhenApiReturnsNull() {
        // Arrange
        when(apiClient.getPercentageFromMock()).thenReturn(null);
        when(percentageCache.get()).thenReturn(10.0);

        // Act
        double result = percentageService.getPercentage();

        // Assert
        assertEquals(10.0, result);
    }

    @Test
    void shouldReturnPercentageFromCacheWhenApiThrowsException() {
        // Arrange
        when(apiClient.getPercentageFromMock()).thenThrow(new RuntimeException("API down"));
        when(percentageCache.get()).thenReturn(12.5);

        // Act
        double result = percentageService.getPercentage();

        // Assert
        assertEquals(12.5, result);
    }

    @Test
    void shouldThrowWhenNoApiAndNoCacheAvailable() {
        // Arrange
        when(apiClient.getPercentageFromMock()).thenThrow(new RuntimeException("API down"));
        when(percentageCache.get()).thenReturn(null);

        // Act & Assert
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            percentageService.getPercentage();
        });
        assertEquals("No se pudo obtener el porcentaje ni del servicio ni de la caché.", exception.getMessage());
    }
}
