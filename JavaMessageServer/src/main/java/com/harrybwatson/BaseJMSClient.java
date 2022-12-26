package com.harrybwatson;

import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSProducer;
import jakarta.jms.TextMessage;
import jakarta.jms.Queue;

import javax.naming.InitialContext;

public class BaseJMSClient {

    public static void main(String[] args) {

        // Create a connection factory
        ConnectionFactory factory = null;
        Queue queue = null;

        try {
            InitialContext initialContext = new InitialContext();

            // Create a connection factory
            factory = (ConnectionFactory) initialContext.lookup("jms/baseConnectionFactory");

            // Create a queue
            queue = (Queue) initialContext.lookup("jms/baseQueue");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (JMSContext jmsContext = factory.createContext()) {

            TextMessage textMessage = jmsContext.createTextMessage("Message Using JMS 2.0");
            JMSProducer jmsProducer = jmsContext.createProducer().send(queue, textMessage);

            TextMessage receivedMessage = (TextMessage) jmsContext.createConsumer(queue).receive();
            System.out.println(receivedMessage.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
