package com.harrybwatson;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.TextMessage;

public class BaseListener implements MessageListener {

    @Override
    public void onMessage(Message message) {

        try {
            TextMessage textMessage = (TextMessage) message;
            System.out.println("Received message: " + textMessage.getText());
        } catch (JMSException e) {
            System.out.println(e);
        }
    }
}
