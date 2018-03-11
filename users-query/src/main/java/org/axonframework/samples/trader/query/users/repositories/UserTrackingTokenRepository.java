package org.axonframework.samples.trader.query.users.repositories;


import org.axonframework.samples.trader.query.users.UserTrackingTokenEntry;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserTrackingTokenRepository extends PagingAndSortingRepository<UserTrackingTokenEntry, String> {
	
	UserTrackingTokenEntry findByUsername(String username);

	UserTrackingTokenEntry findByIdentifier(String identifier);
}
