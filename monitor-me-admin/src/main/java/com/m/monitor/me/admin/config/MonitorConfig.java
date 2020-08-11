package com.m.monitor.me.admin.config;

import com.m.monitor.me.service.transfer.server.MonitorExpressWayServer;
import com.m.monitor.me.service.transfer.server.task.IntegratorSaveTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

@Configuration
public class MonitorConfig {
    @Autowired
    public MongoDbFactory mongoFactory;

    @Autowired
    public MongoMappingContext mongoMappingContext;

    @Bean
    public MappingMongoConverter mongoConverter() throws Exception {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoFactory);
        MappingMongoConverter mongoConverter = new MappingMongoConverter(dbRefResolver, mongoMappingContext);
        //this is my customization
        mongoConverter.setMapKeyDotReplacement("/");
        mongoConverter.afterPropertiesSet();
        return mongoConverter;
    }

    @Bean
    public MonitorExpressWayServer getMonitorExpressWayServer(){
        return MonitorExpressWayServer.getInstance();
    }


    @Bean
    public IntegratorSaveTask integratorSaveTask() {
        return IntegratorSaveTask.getInstance();
    }

}
