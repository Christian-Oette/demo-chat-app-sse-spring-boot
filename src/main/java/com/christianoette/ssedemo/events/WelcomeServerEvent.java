package com.christianoette.ssedemo.events;

public record WelcomeServerEvent(String userName, String colorHex) implements CommunicationEvent {
    @Override
    public String getEventType() {
        return "welcome";
    }
}
