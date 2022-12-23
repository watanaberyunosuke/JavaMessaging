package com.harrybwatson.springmessaging.sms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class SMSReceiver {

    @JmsListener(destination = "SMSInbox", containerFactory = "SMSFactory")
    public void receiveMessage(SMS sms) {
        System.out.println("Received message: " + sms);
    }
}
