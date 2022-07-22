package com.christianoette.ssedemo.events;

import com.christianoette.ssedemo.controller.ChatRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static org.springframework.web.servlet.mvc.method.annotation.SseEmitter.event;

@Component
@Slf4j
public class EventHandler {

    private static final AtomicInteger ID_COUNTER = new AtomicInteger(1);
    public static final long DEFAULT_TIMEOUT = Long.MAX_VALUE;
    private final Set<Client> registeredClients = new HashSet<>();

    public SseEmitter registerClient() {
        var emitter = new SseEmitter(DEFAULT_TIMEOUT);
        var client = new Client(emitter);
        emitter.onCompletion(() -> registeredClients.remove(client));
        emitter.onError((err) -> removeAndLogError(client));
        emitter.onTimeout(() -> removeAndLogError(client));
        registeredClients.add(client);
        sendWelcomeToClient(client);

        log.info("New client registered {}", client.name());
        return emitter;
    }

    private void removeAndLogError(Client client) {
        log.info("Error during communication. Unregister client {}", client.name());
        registeredClients.remove(client);
    }


    private void sendWelcomeToClient(Client client) {
        WelcomeServerEvent welcomeServerEvent = new WelcomeServerEvent(client.name(), client.color());
        sendMessage(client, welcomeServerEvent);
    }

    public void broadcast(ChatRequestDto dto) {
        Set<Client> clients = Set.copyOf(registeredClients);
        for (Client client: clients) {
            ChatServerEvent chatEvent = new ChatServerEvent(dto.message(), dto.userName(), dto.colorHex());
            sendMessage(client, chatEvent);
        }
    }

    private void sendMessage(Client client, CommunicationEvent event) {
        var sseEmitter = client.sseEmitter();
        try {
            log.info("Notify client {}", client.name());
            var eventId = ID_COUNTER.incrementAndGet();
            SseEmitter.SseEventBuilder eventBuilder = event().name(event.getEventType())
                    .id(String.valueOf(eventId))
                    .data(event, MediaType.APPLICATION_JSON);
            sseEmitter.send(eventBuilder);
        } catch (IOException e) {
            sseEmitter.completeWithError(e);
        }
    }

}
