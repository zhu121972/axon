package org.axonframework.samples.trader.query.users.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.axonframework.amqp.eventhandling.AMQPMessageConverter;
import org.axonframework.amqp.eventhandling.DefaultAMQPMessageConverter;
import org.axonframework.amqp.eventhandling.PackageRoutingKeyResolver;
import org.axonframework.amqp.eventhandling.RoutingKeyResolver;
import org.axonframework.amqp.eventhandling.spring.SpringAMQPMessageSource;
import org.axonframework.amqp.eventhandling.spring.SpringAMQPPublisher;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.serialization.Serializer;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
public class Amqpconfig {

	 //   @Autowired
	 //   private SimpleMessageListenerContainer listenerContainer;

	     @Autowired
	     private Serializer serializer;
	      
	      @Autowired
	      RoutingKeyResolver routingKeyResolver;
	    
	    @Autowired
	    private SpringAMQPMessageSource springAMQPMessageSource;

	    @Autowired
	    private AMQPMessageConverter messageConverter;

	  
//	    @Autowired
//	    private SpringAMQPPublisher AMQPPublisher;
	    
	    @Autowired
	    private SimpleEventBus AmqpEventBus;
	    
	    @Autowired
	    private ConnectionFactory connectionFactory;
	    
	    

	    @Bean
	    public RoutingKeyResolver routingKeyResolver() {
	        return new PackageRoutingKeyResolver();
	    }

        @Bean
        public AMQPMessageConverter messageConverter(Serializer Serializer ,
                RoutingKeyResolver routingKeyResolver) {
            return new DefaultAMQPMessageConverter(serializer, routingKeyResolver, true);
        }    
	    
	    @Bean
       public SpringAMQPMessageSource springAMQPMessageSource() {
            return new SpringAMQPMessageSource(messageConverter);
        }
	    
	    @Bean
	    public ConnectionFactory connectionFactory() {	   
	    	       
	    	CachingConnectionFactory con = new CachingConnectionFactory("localhost");
	    	//con
	    	con.setUsername("guest");
	    	con.setPassword("guest");
	        return con;
	    }
	    
	 /*   @Bean
        public SimpleMessageListenerContainer rabbitListener() {
            SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer(connectionFactory());
            listenerContainer.setQueueNames("testQueue");
            listenerContainer.setAutoStartup(false);
            return listenerContainer;
        }
	    */

	    
	    @Bean
        public AmqpAdmin amqpAdmin() {
	    	 AmqpAdmin	t = new RabbitAdmin(connectionFactory());
	    	 t.declareQueue(new Queue("testQueue", false, false, true));
	         t.declareExchange(new FanoutExchange("Axon.EventBus", false, true));
	     //    t.declareExchange(new TopicExchange("Axon.EventBus", false, false));
	         t.declareBinding(new Binding("testQueue", Binding.DestinationType.QUEUE, "Axon.EventBus", "", null));
            return t;
        }
	    
	   @Bean
	    public SimpleEventBus eventBus(){
	    	return new SimpleEventBus();
	    }
	    
	    
	/*    @Bean(initMethod = "start", destroyMethod = "shutDown")
	    public SpringAMQPPublisher amqpBridge(SimpleEventBus SimpleEventBus, ConnectionFactory connectionFactory,  AMQPMessageConverter amqpMessageConverter) {
	        SpringAMQPPublisher publisher = new SpringAMQPPublisher(SimpleEventBus);
	        publisher.setExchangeName("Axon.EventBus");
	        publisher.setConnectionFactory(connectionFactory);
	        publisher.setMessageConverter(amqpMessageConverter); 
	        return publisher;
	    }
	  */ 
	    
}
