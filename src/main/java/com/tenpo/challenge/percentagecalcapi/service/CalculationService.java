package com.tenpo.challenge.percentagecalcapi.service;


import com.tenpo.challenge.percentagecalcapi.model.CalculationRequest;
import com.tenpo.challenge.percentagecalcapi.model.CalculationResponse;

public interface CalculationService {
    CalculationResponse calculateWithPercentage(CalculationRequest request);
}
