/*
 * Copyright (c) 2010-2016. Axon Framework
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.axonframework.samples.trader.query.users.config;

import org.axonframework.amqp.eventhandling.AMQPMessageConverter;
import org.axonframework.amqp.eventhandling.spring.SpringAMQPPublisher;
import org.axonframework.common.transaction.NoTransactionManager;
import org.axonframework.eventhandling.EventProcessor;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventhandling.SimpleEventHandlerInvoker;
import org.axonframework.eventhandling.SubscribingEventProcessor;
import org.axonframework.eventhandling.TrackingEventProcessor;
import org.axonframework.eventhandling.tokenstore.TokenStore;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.samples.trader.query.users.UserAmqpListerner;
import org.axonframework.samples.trader.query.users.UserListener;
import org.axonframework.samples.trader.query.users.UserTrackingListener;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsersQueryConfig {

    @Autowired
    private EventStore eventStore;

    @Autowired
    private UserListener userListener;
    
    @Autowired
    private UserTrackingListener userTrackingListener;
    
    @Autowired
    private UserAmqpListerner userAmqpListener;
    
    @Autowired
    private TokenStore tokenStore;
    
    @Autowired
    private SimpleEventBus AmqpEventBus;
    
  //  @Autowired
 //   private SpringAMQPPublisher publisher;
  //  @Autowired
 //   private AmqpAdmin amqpAdmin;
    
    @Autowired
    private ConnectionFactory connectionFactory;
    
    @Autowired
    private AMQPMessageConverter amqpMessageConverter;
    
    @Bean
    public EventProcessor usersQueryEventProcessor() {
        SubscribingEventProcessor eventProcessor = new SubscribingEventProcessor("usersQueryEventProcessor",
                                                                                 new SimpleEventHandlerInvoker(
                                                                                         userListener),
                                                                                 eventStore);
                                                                                 
    	/*  SubscribingEventProcessor eventProcessor = new SubscribingEventProcessor("usersAmqpEventProcessor",
                  new SimpleEventHandlerInvoker(
                 		 userAmqpListener),
                  AmqpEventBus);*/
    	  eventProcessor.start();
        SpringAMQPPublisher publisher = new SpringAMQPPublisher(eventStore);
        publisher.setExchangeName("Axon.EventBus");
        publisher.setConnectionFactory(connectionFactory);
        publisher.setMessageConverter(amqpMessageConverter); 
        publisher.start();
        

        return eventProcessor;
    }
    @Bean
    public EventProcessor usersTrackingTokenProcessor() {
        TrackingEventProcessor eventProcessor = new TrackingEventProcessor("usersQueryEventProcessor",
                                                                                 new SimpleEventHandlerInvoker(
                                                                                		 userTrackingListener),
                                                                                 eventStore,tokenStore,NoTransactionManager.INSTANCE);
        eventProcessor.start();

        return eventProcessor;
    }
    
   @Bean
    public EventProcessor usersAmqpProcessor() {
	   SubscribingEventProcessor eventProcessor = new SubscribingEventProcessor("usersAmqpEventProcessor",
                                                                                 new SimpleEventHandlerInvoker(
                                                                                		 userAmqpListener),
                                                                                 AmqpEventBus);
     eventProcessor.start();
     
        return eventProcessor;
    }
    
    
}
