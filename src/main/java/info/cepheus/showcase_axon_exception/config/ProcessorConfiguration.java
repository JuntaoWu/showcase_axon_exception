package info.cepheus.showcase_axon_exception.config;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.config.EventProcessingConfigurer;
import org.axonframework.eventhandling.TrackingEventProcessorConfiguration;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class ProcessorConfiguration {

    @Autowired
    public void config(EventProcessingConfigurer configurer) {
        configurer.registerDefaultListenerInvocationErrorHandler(conf -> new MyAxonErrorHandler())
                .registerDefaultErrorHandler(conf -> new MyAxonErrorHandler());
    }
}
