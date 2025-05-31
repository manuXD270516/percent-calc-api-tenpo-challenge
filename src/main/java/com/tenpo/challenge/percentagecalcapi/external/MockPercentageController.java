package com.tenpo.challenge.percentagecalcapi.external;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class MockPercentageController {

    @GetMapping("/mock/percentage")
    public double getPercentage() {
        return new Random().nextInt(6) + 5; // devuelve un valor entre 5% y 10%
    }
}
