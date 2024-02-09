package com.cpe.fire.domain.message;

import com.cpe.fire.domain.event.Event;

public class Message<T> {
    Event event;
    T object;

    public Message(T event, T incidents) {
    }
}
