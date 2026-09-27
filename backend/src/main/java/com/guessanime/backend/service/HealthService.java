package com.guessanime.backend.service;

import org.springframework.stereotype.Service;

@Service
public class HealthService {

    public String getHealth() {
        return "OK";
    }
}