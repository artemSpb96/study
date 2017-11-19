package com.epam.spring.core;

import com.epam.spring.core.beans.Client;
import com.epam.spring.core.beans.Event;
import com.epam.spring.core.loggers.EventLogger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

public class App {

    private Client client;
    private EventLogger defaultLogger;
    private Map<EventType, EventLogger> loggerByType;

    public App(Client client, EventLogger defaultLogger, Map<EventType, EventLogger> loggerByType) {
        this.client = client;
        this.defaultLogger = defaultLogger;
        this.loggerByType = loggerByType;
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

        App app = (App)context.getBean("app");

        app.logEvent((Event) context.getBean("event"), EventType.ERROR);

        context.close();
    }

    private void logEvent(Event event, EventType eventType) {
        String message = event.getMessage();
        event.setMessage(message.replaceAll(client.getId(), client.getFullName()));

        EventLogger logger = loggerByType.get(eventType);
        if (logger == null) {
            logger = defaultLogger;
        }

        logger.logEvent(event);
    }
}
