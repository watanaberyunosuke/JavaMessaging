package com.harrybwatson;

import jakarta.jms.*;

import javax.naming.InitialContext;

public class BaseReceiver {

    public static void main(String[] args) throws Exception {

        try {
            // Create and start connection
            InitialContext context = new InitialContext();
            QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory) context.lookup("baseConnectionFactory");
            QueueConnection connection = queueConnectionFactory.createQueueConnection();
            connection.start();

            // Create a Queue Session
            QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

            // Get Queue Object
            Queue queue = (Queue) context.lookup("baseQueue");

            // Create a receiver
            QueueReceiver receiver = session.createReceiver(queue);

            // Create Listener
            BaseListener listener = new BaseListener();

            // Register Listener
            receiver.setMessageListener(listener);

            System.out.println("Receiver 1 is ready...");
            System.out.println("Press ctrl-c to exit...");

            while (true) {
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
