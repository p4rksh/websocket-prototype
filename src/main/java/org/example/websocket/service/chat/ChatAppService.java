package org.example.websocket.service.chat;

import lombok.RequiredArgsConstructor;
import org.example.websocket.controller.resource.MessageReq;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatAppService {

    private final List<ChatMessageProcessor> processors;

    public void send(MessageReq req) {
        for (var processor: processors) {
            processor.execute(req);
        }
    }
}
