package org.axonframework.samples.trader.query.users;

import java.io.Serializable;

import org.axonframework.samples.trader.api.users.UserAccount;
import org.springframework.data.annotation.Id;

public class UserTrackingTokenEntry implements UserAccount, Serializable  {
	    @Id
	    @javax.persistence.Id
	    private String identifier;
	    private String name;
	    private String username;
	    private String password;
		public String getIdentifier() {
			return identifier;
		}
		public void setIdentifier(String identifier) {
			this.identifier = identifier;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		@Override
		public String getUserId() {
			// TODO Auto-generated method stub
			return this.identifier;
		}
		@Override
		public String getUserName() {
			// TODO Auto-generated method stub
			return this.username;
		}
		@Override
		public String getFullName() {
			// TODO Auto-generated method stub
			return this.name;
		}
	    
	   

}
