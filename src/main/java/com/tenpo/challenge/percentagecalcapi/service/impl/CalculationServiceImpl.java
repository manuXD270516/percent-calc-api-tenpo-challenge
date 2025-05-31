package com.tenpo.challenge.percentagecalcapi.service.impl;


import com.tenpo.challenge.percentagecalcapi.model.CalculationRequest;
import com.tenpo.challenge.percentagecalcapi.model.CalculationResponse;
import com.tenpo.challenge.percentagecalcapi.service.CalculationService;
import com.tenpo.challenge.percentagecalcapi.service.PercentageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalculationServiceImpl implements CalculationService {

    private final PercentageService percentageService;

    @Override
    public CalculationResponse calculateWithPercentage(CalculationRequest request) {
        double sum = request.getNum1() + request.getNum2();
        double percentage = percentageService.getPercentage();
        double result = sum + (sum * (percentage / 100));

        log.info("Calculating: {} + {} = {}, applying {}% → final result = {}",
                request.getNum1(), request.getNum2(), sum, percentage, result);

        return new CalculationResponse(result, percentage);
    }
}
