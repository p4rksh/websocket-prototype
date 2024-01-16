package org.example.websocket.controller.resource;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TextMessageReq extends MessageReq<String> {

    @Override
    public String getPayloadString() {
        return getPayload();
    }
}
