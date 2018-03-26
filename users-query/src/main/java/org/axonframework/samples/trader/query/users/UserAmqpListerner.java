package org.axonframework.samples.trader.query.users;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.samples.trader.api.users.UserCreatedEvent;
//import org.hibernate.annotations.common.util.impl.Log_.logger;
//import org.axonframework.samples.trader.query.users.repositories.UserTrackingTokenRepository;
//import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UserAmqpListerner {
//	private UserTrackingTokenRepository userRepository;
	  private final static Logger logger = LoggerFactory.getLogger( UserAmqpListerner.class);
    @EventHandler
    public void handleUserCreated(UserCreatedEvent event) {
    	 logger.debug("Amqp_UserCreatedEvent {}", event.getName());

    }

}
