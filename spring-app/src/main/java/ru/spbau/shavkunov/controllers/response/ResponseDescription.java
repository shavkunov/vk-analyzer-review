package ru.spbau.shavkunov.controllers.response;

import org.jetbrains.annotations.NotNull;

/**
 * Response description. ErrorType used to distinguish link errors and posts amount error.
 * For front-end it's easier to show error in appropriate place.
 * This class should be a enum, but Spring Boot doesn't convert it to json properly.
 */
public class ResponseDescription {
    /**
     * Standard errors.
     */
    public static final @NotNull String OK = "OK";
    public static final @NotNull String INVALID_AMOUNT = "Invalid amount. Please, set from 10 to 80";
    public static final @NotNull String INVALID_LINK = "Invalid page link";
    public static final @NotNull String EMPTY_LINK = "Please, enter link to vk user/community";
    public static final @NotNull String HIDDEN_WALL = "User hid his wall from accessing from outside";
    public static final @NotNull String BANNED_USER = "User was deleted or banned";
    public static final @NotNull String INTERNAL_ERROR = "UNKNOWN ERROR";

    private @NotNull String description;
    private @NotNull ErrorType type;

    public ResponseDescription(@NotNull String value) {
        this.description = value;

        if (description.equals(INVALID_AMOUNT)) {
            type = ErrorType.AMOUNT;
        }

        if (description.equals(INVALID_LINK)) {
            type = ErrorType.LINK;
        }

        if (description.equals(EMPTY_LINK)) {
            type = ErrorType.LINK;
        }
    }

    ResponseDescription() {
    }

    public ErrorType getType() {
        return type;
    }

    public @NotNull String getDescription() {
        return description;
    }
}
