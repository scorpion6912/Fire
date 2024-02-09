package com.cpe.fire.domain.event;

public class Event {
    EventType type;
    EventStatus status;

    public Event(EventType eventType, EventStatus eventStatus) {
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

}
