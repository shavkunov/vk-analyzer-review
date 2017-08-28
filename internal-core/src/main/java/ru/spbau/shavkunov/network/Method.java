package ru.spbau.shavkunov.network;

import org.jetbrains.annotations.NotNull;

/**
 * Enum describes the type of the method which will be used to get vk wall information.
 */
public enum Method {
    GROUP_GET_BY_ID("groups.getById"),
    USERS_GET("users.get"),
    WALL_GET("wall.get"),
    PHOTO_GET_BY_ID("photos.getById");

    private @NotNull String value;

    Method(@NotNull String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.getValue();
    }

    public String getValue() {
        return value;
    }
}
