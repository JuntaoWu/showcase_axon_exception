package info.cepheus.showcase_axon_exception.domain.command;

import info.cepheus.showcase_axon_exception.coreapi.command.CreatePersonCommand;
import info.cepheus.showcase_axon_exception.coreapi.event.PersonCreatedEvent;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.stereotype.Component;

@Slf4j
// Required by Axon to build a default Aggregate prior to Event Sourcing
@NoArgsConstructor
@Aggregate
@Component
public class PersonAggregate {
    @AggregateIdentifier
    private String personId;

    private String name;

    @CommandHandler
    public PersonAggregate(CreatePersonCommand command) {
        PersonCreatedEvent event = new PersonCreatedEvent(
                command.getPersonId(),
                command.getName()
        );
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void on(PersonCreatedEvent event) {
        this.personId = event.getPersonId();
        this.name = event.getName();
    }

}
