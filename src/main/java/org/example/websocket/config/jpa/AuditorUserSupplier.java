package org.example.websocket.config.jpa;

import lombok.Builder;

import java.util.Optional;
import java.util.function.Supplier;

@Builder
public class AuditorUserSupplier {

    private Supplier<String> supplier;

    public Optional<String> getUserName() {
        return Optional.ofNullable(supplier.get());
    }
}