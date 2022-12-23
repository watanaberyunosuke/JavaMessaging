package com.harrybwatson.springmessaging;

import com.harrybwatson.springmessaging.sms.SMS;
import jakarta.jms.ConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@SpringBootApplication
@EnableJms
public class SpringMessagingApplication {

    @Bean
    public JmsListenerContainerFactory<?> SMSFactory(
            @Qualifier("jmsConnectionFactory") ConnectionFactory connectionFactory,
            DefaultJmsListenerContainerFactoryConfigurer configurer
    ) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();

        configurer.configure(factory, connectionFactory);

        return factory;
    }

    @Bean
    public MessageConverter jacksonMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

    public static void main(String[] args) throws IOException {
        ConfigurableApplicationContext context = SpringApplication.run(SpringMessagingApplication.class, args);
        JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);

        while (true) {
            try {
                Thread.sleep(500);
                // Read Message
                System.out.println("Input Message: ");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                String bodyMessage = bufferedReader.readLine();

                // Send message with POJO
                System.out.println("Sending message");
                jmsTemplate.convertAndSend("SMSInbox", new SMS("ReceiverUser", bodyMessage));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
