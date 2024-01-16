package org.example.websocket.protocol.chat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.websocket.protocol.MessageEvent;

@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Getter
public class NoticeMessageEvent extends MessageEvent<String> {

    @Override
    public String getContext() { return getRoomId(); }
}
