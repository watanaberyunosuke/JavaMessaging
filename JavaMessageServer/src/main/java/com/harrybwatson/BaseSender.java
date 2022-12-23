package com.harrybwatson;

import jakarta.jms.*;

import javax.naming.InitialContext;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BaseSender {

    public static void main(String[] args) throws Exception {

        try {

            // Create and start connection
            InitialContext initialContext = new InitialContext();
            QueueConnectionFactory qConFactory = (QueueConnectionFactory) initialContext.lookup("baseConnectionFactory");
            QueueConnection connection = qConFactory.createQueueConnection();
            connection.start();

            // Create queue session
            QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

            // Get Queue Object
            Queue queue = (Queue) initialContext.lookup("baseQueue");

            // Create QueueSender Object
            QueueSender sender = session.createSender(queue);

            // Create TextMessage Object
            TextMessage message = session.createTextMessage();

            // Write Message from Console
            BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                System.out.println("Enter message: ");
                System.out.println("Send end to End Session");
                String messageText = bReader.readLine();

                if (messageText.equals("end")) {
                    break;
                }
                message.setText(messageText);
                sender.send(message);

                System.out.println("Message sent");
            }
            connection.close();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}
