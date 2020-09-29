package com.shine.faas.common;

import org.springframework.context.ApplicationEvent;

public class ApplicationStartedEvent extends ApplicationEvent {
    public ApplicationStartedEvent(Object source) {
        super(source);
    }
}
