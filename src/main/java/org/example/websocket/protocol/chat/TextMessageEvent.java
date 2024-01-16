package org.example.websocket.protocol.chat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.websocket.protocol.MessageEvent;

@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Getter
public class TextMessageEvent extends MessageEvent<String> {

    private String senderId;
    private String senderName;
    private String senderImageUrl;

    @Override
    public String getContext() {
        return getRoomId();
    }
}
