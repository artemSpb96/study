package com.epam.spring.core;

import com.epam.spring.core.beans.Client;
import com.epam.spring.core.beans.Event;
import com.epam.spring.core.loggers.ConsoleEventLogger;
import com.epam.spring.core.loggers.EventLogger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    private Client client;
    private EventLogger eventLogger;

    public App(Client client, EventLogger eventLogger) {
        this.client = client;
        this.eventLogger = eventLogger;
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

        App app = (App)context.getBean("app");

        app.logEvent((Event) context.getBean("event"));

        context.close();
    }

    private void logEvent(Event event) {
        String message = event.getMessage();
        event.setMessage(message.replaceAll(client.getId(), client.getFullName()));
        eventLogger.logEvent(event);
    }
}
