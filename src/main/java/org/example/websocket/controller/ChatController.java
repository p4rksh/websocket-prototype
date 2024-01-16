package org.example.websocket.controller;

import lombok.RequiredArgsConstructor;
import org.example.websocket.controller.resource.NoticeMessageReq;
import org.example.websocket.controller.resource.TextMessageReq;
import org.example.websocket.service.chat.ChatAppService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatAppService service;

    @MessageMapping("/chat/text")
    public void text(TextMessageReq req) {
        service.send(req);
    }

    @MessageMapping("/chat/notice")
    public void notice(NoticeMessageReq req) {
        service.send(req);
    }
}
