package org.axonframework.samples.trader.query.users;


import org.axonframework.amqp.eventhandling.spring.SpringAMQPMessageSource;
import org.axonframework.eventhandling.SimpleEventBus;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.stereotype.Component;
            

public class RabbitListenerInit {

     
	 public   RabbitListenerInit(ConnectionFactory connectionFactory, SpringAMQPMessageSource springAMQPMessageSource) {
          SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer(connectionFactory);
          listenerContainer.setQueueNames("testQueue");
          listenerContainer.setAutoStartup(false);
          listenerContainer.setMessageListener(springAMQPMessageSource);
          listenerContainer.start();
         
      }
	
}
