package org.example.websocket.controller.resource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Getter
public abstract class MessageReq<T> {

    private String roomId;
    private String senderId;
    private MessageType type;
    private T payload;

    public abstract String getPayloadString();
}
