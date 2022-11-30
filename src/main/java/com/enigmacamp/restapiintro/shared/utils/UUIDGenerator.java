package com.enigmacamp.restapiintro.shared.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUIDGenerator implements IRandomStringGenerator {

    @Override
    public String get() {
        return UUID.randomUUID().toString();
    }
}
