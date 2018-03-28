package org.axonframework.samples.trader.query.users;


import org.axonframework.eventhandling.SimpleEventBus;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.stereotype.Component;
            

public class RabbitListenerInit {

     
	 public   RabbitListenerInit(ConnectionFactory connectionFactory, SimpleEventBus eventBus) {
          SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer(connectionFactory);
          listenerContainer.setQueueNames("testQueue");
          listenerContainer.setAutoStartup(false);
          listenerContainer.setMessageListener(eventBus);
          listenerContainer.start();
         
      }
	
}
