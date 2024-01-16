package org.example.websocket.service.chat;

import org.example.websocket.controller.resource.MessageReq;
import org.example.websocket.entity.chat.ChatMessageEntity;

public abstract class ChatMessageDispatcher {

    public void dispatch(MessageReq req) {
        publish(write(req));
    }

    protected abstract ChatMessageEntity write(MessageReq req);
    protected abstract void publish(ChatMessageEntity entity);
}
