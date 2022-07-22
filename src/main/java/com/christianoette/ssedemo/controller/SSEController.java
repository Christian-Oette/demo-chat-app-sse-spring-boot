package com.christianoette.ssedemo.controller;

import com.christianoette.ssedemo.events.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SSEController {

    private final EventHandler eventHandler;

    @PostMapping("/message")
    public void sendMessage(@RequestBody ChatRequestDto messageDto) {
        eventHandler.broadcast(messageDto);
    }

    @GetMapping("/register-client")
    public SseEmitter sseEmitter() {
        return eventHandler.registerClient();
    }
}
