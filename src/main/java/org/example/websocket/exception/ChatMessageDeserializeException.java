package org.example.websocket.exception;


import javax.annotation.Nullable;

public class ChatMessageDeserializeException extends RuntimeException {

    public ChatMessageDeserializeException(@Nullable String message) {
        super(message);
    }
}
