package info.cepheus.showcase_axon_exception.infrastructure;

import info.cepheus.showcase_axon_exception.application.command.PersonService;
import info.cepheus.showcase_axon_exception.coreapi.command.CreatePersonCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class PersonServiceImpl implements PersonService {
    private final CommandGateway commandGateway;

    @Override
    public void createUniquePerson(String personId, String name) {
        commandGateway.send(new CreatePersonCommand(UUID.randomUUID().toString(), name));
    }
}
