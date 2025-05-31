package com.tenpo.challenge.percentagecalcapi.model;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class CalculationResponse {
    private double result;
    private double percentageApplied;
}