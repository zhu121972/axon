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

package org.axonframework.samples.trader.infra.config;

import org.axonframework.eventhandling.tokenstore.TokenStore;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.mongo.eventhandling.saga.repository.MongoSagaStore;
import org.axonframework.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.mongo.eventsourcing.tokenstore.MongoTokenStore;
import org.axonframework.serialization.Serializer;
import org.axonframework.serialization.xml.XStreamSerializer;
import org.axonframework.mongo.MongoTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("mongodb")
public class CQRSInfrastructureMongoDBConfig {

 //   @Autowired
  //  private MongoTemplate eventStoreMongoTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;
    
    @Autowired
    private Serializer serializer;
    
    @Autowired
    private TokenStore tokenStore;
  

    @Bean
    public EventStore eventStore() {
        return new EmbeddedEventStore(eventStorageEngine());
    }

    @Bean
    public MongoEventStorageEngine eventStorageEngine() {
        return new MongoEventStorageEngine(mongoTemplate);
    }
    
    @Bean
    public Serializer serializer() {
        return new XStreamSerializer();
    }
     
   @Bean
   public TokenStore tokenStore() {
        return new MongoTokenStore(mongoTemplate, serializer);
    }
    
    
    
    @Bean
    public MongoSagaStore sagaRepository() {
        return new MongoSagaStore(mongoTemplate);
    }
}