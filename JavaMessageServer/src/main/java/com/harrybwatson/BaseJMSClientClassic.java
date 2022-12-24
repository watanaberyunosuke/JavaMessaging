package com.harrybwatson;

import jakarta.jms.*;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class BaseJMSClientClassic {

    public static void main(String[] args) throws NamingException {
        InitialContext context = null;

        try {
            context = new InitialContext();

            // Create a connection factory, connection and session
            ConnectionFactory factory = (ConnectionFactory) context.lookup("jms/baseConnectionFactory");
            Connection connection = factory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create a queue
            Queue queue = (Queue) context.lookup("jms/baseQueue");

            // Create a Producer
            MessageProducer producer = session.createProducer(queue);

            // Create a message
            TextMessage textMessage = session.createTextMessage("Test Message: Hello World!");

            // Send the message
            producer.send(textMessage);

            // Create a consumer
            MessageConsumer consumer = session.createConsumer(queue);
            connection.start();

            // Receive the message
            TextMessage message = (TextMessage) consumer.receive();

            System.out.println(message.getText());

        } catch (NamingException | JMSException e) {
            e.printStackTrace();
        } finally {

            if (context != null) {
                context.close();
            }
            
        }
    }
}
