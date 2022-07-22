package com.christianoette.ssedemo.events;

import com.christianoette.ssedemo.forfun.ColorGenerator;
import com.christianoette.ssedemo.forfun.NameGenerator;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public record Client(SseEmitter sseEmitter,
                     String name,
                     String color) {

    public Client(SseEmitter sseEmitter) {
        this(sseEmitter, NameGenerator.generateName(), ColorGenerator.generateColor());
    }
}
