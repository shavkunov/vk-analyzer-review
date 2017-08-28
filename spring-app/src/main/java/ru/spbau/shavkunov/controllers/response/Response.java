package ru.spbau.shavkunov.controllers.response;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.spbau.shavkunov.controllers.response.exceptions.WrongResponseInitException;

import static ru.spbau.shavkunov.controllers.response.ResponseDescription.OK;

/**
 * Represents data sent to user. If request is correct then type is OK and data is a stats object.
 * Otherwise need to consider description. Client can access the cause of the error.
 */
public class Response {
    private @NotNull ResponseType type;
    private @NotNull ResponseDescription description;
    private @Nullable Object data;

    public Response(@NotNull String description) throws WrongResponseInitException {
        if (!description.equals(OK)) {
            handleDescription(description);
            data = null;
            return;
        }

        throw new WrongResponseInitException();
    }

    public Response(@NotNull Object data) throws WrongResponseInitException {
        handleDescription(OK);
        this.data = data;
    }

    public Response(@NotNull String description, @Nullable Object data) {
        handleDescription(description);
        this.data = data;
    }

    private void handleDescription(@NotNull String description) {
        this.description = new ResponseDescription(description);
        if (description.equals(OK)) {
            type = ResponseType.OK;
        } else {
            type = ResponseType.ERROR;
        }
    }

    public ResponseType getType() {
        return type;
    }

    public ResponseDescription getDescription() {
        return description;
    }

    public Object getData() {
        return data;
    }
}