package org.axonframework.samples.trader.orders.config;


import org.axonframework.amqp.eventhandling.spring.SpringAMQPMessageSource;
import org.axonframework.samples.trader.orders.command.BuyTradeManagerSaga;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.rabbitmq.client.Channel;

import org.axonframework.serialization.Serializer;

@Configuration
@PropertySource("classpath:application.properties")
public class AMQPConfiguration {
  @Value("${axon.amqp.exchange}")
  private String exchangeName;

//  @Bean
//  public Queue productQueue(){
//      return new Queue("product", true);
//  }

  @Bean
  public Queue orderQueue(){
      return new Queue("order",true);
  }

  @Bean
  public Exchange exchange(){
      return ExchangeBuilder.topicExchange(exchangeName).durable(true).build();
  }

//  @Bean
//  public Binding productQueueBinding() {
//      return BindingBuilder.bind(productQueue()).to(exchange()).with("#.product.#").noargs();
// }

  @Bean
  public Binding orderQueueBinding() {
      return BindingBuilder.bind(orderQueue()).to(exchange()).with("#.order.#").noargs();
  }
  
  @Bean
  public SpringAMQPMessageSource orderQueueMessageSource(Serializer serializer){
      return new SpringAMQPMessageSource(serializer){
          @RabbitListener(queues = "order")
          @Override
          public void onMessage(Message message, Channel channel) throws Exception {
        //	  logger.debug("Order message received: "+message.toString());
              super.onMessage(message, channel);
          }
      };
  
  
  }
  
}