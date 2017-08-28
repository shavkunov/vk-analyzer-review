package ru.spbau.shavkunov.controllers.response;

import org.jetbrains.annotations.NotNull;

/**
 * Type of response.
 */
public enum ResponseType {
    OK("OK"),
    ERROR("ERROR");

    private @NotNull String value;

    ResponseType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
