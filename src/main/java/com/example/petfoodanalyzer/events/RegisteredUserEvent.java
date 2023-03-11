package com.example.petfoodanalyzer.events;

import org.springframework.context.ApplicationEvent;

public class RegisteredUserEvent extends ApplicationEvent {
    private String email;

    public RegisteredUserEvent(String email) {
        super(email);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public RegisteredUserEvent setEmail(String email) {
        this.email = email;
        return this;
    }
}
