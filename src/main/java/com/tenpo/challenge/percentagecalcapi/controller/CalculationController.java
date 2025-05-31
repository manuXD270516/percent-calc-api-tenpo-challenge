package com.tenpo.challenge.percentagecalcapi.controller;

import com.tenpo.challenge.percentagecalcapi.async.HistoryLogger;
import com.tenpo.challenge.percentagecalcapi.model.CalculationRequest;
import com.tenpo.challenge.percentagecalcapi.model.CalculationResponse;
import com.tenpo.challenge.percentagecalcapi.model.EndpointType;
import com.tenpo.challenge.percentagecalcapi.service.CalculationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/calculate")
@RequiredArgsConstructor
public class CalculationController {

    private final CalculationService calculationService;
    private final HistoryLogger historyLogger;

    @PostMapping
    public ResponseEntity<CalculationResponse> calculate(@Valid @RequestBody CalculationRequest request) {
        try {
            CalculationResponse response = calculationService.calculateWithPercentage(request);

            historyLogger.log("/api/v1/calculate", request.toString(), response.toString(), true, EndpointType.CALCULATION);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            historyLogger.log("/api/v1/calculate", request.toString(), e.getMessage(), false, EndpointType.CALCULATION);
            throw e;
        }
    }
}
