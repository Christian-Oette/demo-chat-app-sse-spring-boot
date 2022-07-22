package com.christianoette.ssedemo.events;

public record ChatServerEvent(String message,
                              String userName,
                              String colorHex)
        implements CommunicationEvent {

    @Override
    public String getEventType() {
        return "chat";
    }
}
