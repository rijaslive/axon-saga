package com.demo.saga.order.conf;

import org.axonframework.config.ConfigurationScopeAwareProvider;
import org.axonframework.deadline.DeadlineManager;
import org.axonframework.deadline.SimpleDeadlineManager;
import org.axonframework.eventsourcing.EventCountSnapshotTriggerDefinition;
import org.axonframework.eventsourcing.SnapshotTriggerDefinition;
import org.axonframework.eventsourcing.Snapshotter;
import org.axonframework.spring.messaging.unitofwork.SpringTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class AxonConfig {


//    /*@Bean
//    public MongoTransactionManager transactionManager(MongoDatabaseFactory dbFactory) {
//        return new MongoTransactionManager(dbFactory);
//    }
//
//    @Bean
//    public SpringTransactionManager springTransactionManager(PlatformTransactionManager transactionManager) {
//        return new SpringTransactionManager(transactionManager);
//    }
//
//    @Bean
//    public DeadlineManager deadlineManager(org.axonframework.config.Configuration configuration, SpringTransactionManager transactionManager) {
//        return SimpleDeadlineManager.builder()
//                .scopeAwareProvider(new ConfigurationScopeAwareProvider(configuration))
//                .transactionManager(transactionManager)
//                .build();
//    }*/


    @Bean
    public DeadlineManager deadlineManager(org.axonframework.config.Configuration configuration) {
        // Use a non-transactional deadline manager
        return SimpleDeadlineManager.builder()
                .scopeAwareProvider(new ConfigurationScopeAwareProvider(configuration))
                .build();
    }

    @Bean
    public SnapshotTriggerDefinition orderSnapshotTrigger(Snapshotter snapshotter) {
        return new EventCountSnapshotTriggerDefinition(snapshotter, 5); // Snapshot every 5 events
    }


}