package org.axonframework.samples.trader.query.users;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.samples.trader.api.users.UserCreatedEvent;
//import org.axonframework.samples.trader.query.users.repositories.UserTrackingTokenRepository;
//import org.springframework.beans.factory.annotation.Autowired;

public class UserAmqpListerner {
//	private UserTrackingTokenRepository userRepository;

    @EventHandler
    public void handleUserCreated(UserCreatedEvent event) {
    	UserTrackingTokenEntry userTrackingTokenEntry = new UserTrackingTokenEntry();
        userTrackingTokenEntry.setIdentifier(event.getUserIdentifier().toString());
        userTrackingTokenEntry.setName(event.getName());
        userTrackingTokenEntry.setUsername(event.getUsername());
        userTrackingTokenEntry.setPassword(event.getPassword());

    }

}
