package com.tenpo.challenge.percentagecalcapi.service.impl;

import com.tenpo.challenge.percentagecalcapi.cache.PercentageCache;
import com.tenpo.challenge.percentagecalcapi.external.PercentageApiClient;
import com.tenpo.challenge.percentagecalcapi.service.PercentageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PercentageServiceImpl implements PercentageService {

    private final PercentageApiClient apiClient;
    private final PercentageCache percentageCache;

    @Override
    public double getPercentage() {
        try {
            Double percentage = apiClient.getPercentageFromMock();
            if (percentage != null) {
                percentageCache.save(percentage);
                return percentage;
            } else {
                return getFromCacheOrThrow();
            }
        } catch (Exception ex) {
            log.warn("Fallo el servicio externo, usando valor en caché si existe...");
            return getFromCacheOrThrow();
        }
    }

    private double getFromCacheOrThrow() {
        Double cached = percentageCache.get();
        if (cached != null) {
            return cached;
        }
        throw new IllegalStateException("No se pudo obtener el porcentaje ni del servicio ni de la caché.");
    }

}
