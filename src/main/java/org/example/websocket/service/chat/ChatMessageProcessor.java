package org.example.websocket.service.chat;

import org.example.websocket.controller.resource.MessageReq;

public abstract class ChatMessageProcessor {

    public void execute(MessageReq req) {
        write(req);
        publish(req);
    }

    protected abstract void write(MessageReq req);
    protected abstract void publish(MessageReq req);
}
