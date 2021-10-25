package info.cepheus.showcase_axon_exception.api.command.person;

import info.cepheus.showcase_axon_exception.application.command.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/person")
public class PersonController {

    private final PersonService personService;

    @PostMapping(value = "")
    public ResponseEntity create(@RequestBody CreatePersonDto body) {
        // create a person with given name, which should be unique.
        String personId = UUID.randomUUID().toString();
        personService.createUniquePerson(personId, body.getName());

        return ResponseEntity.created(URI.create("/api/person/" + personId)).build();
    }
}
