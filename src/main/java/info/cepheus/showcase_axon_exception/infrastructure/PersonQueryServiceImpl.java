package info.cepheus.showcase_axon_exception.infrastructure;

import info.cepheus.showcase_axon_exception.application.query.PersonQueryService;
import info.cepheus.showcase_axon_exception.coreapi.event.PersonCreatedEvent;
import info.cepheus.showcase_axon_exception.coreapi.exception.CheckedDataAccessException;
import info.cepheus.showcase_axon_exception.coreapi.exception.NullOrEmptyNameException;
import info.cepheus.showcase_axon_exception.domain.query.personInformation.PersonInformation;
import info.cepheus.showcase_axon_exception.domain.query.personInformation.PersonInformationRepository;
import info.cepheus.showcase_axon_exception.domain.query.personSnapshot.PersonSnapshot;
import info.cepheus.showcase_axon_exception.domain.query.personSnapshot.PersonSnapshotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class PersonQueryServiceImpl implements PersonQueryService {

    private final PersonInformationRepository personInformationRepository;

    private final PersonSnapshotRepository personSnapshotRepository;

    @Transactional
    @Override
    public void save(PersonCreatedEvent event) throws CheckedDataAccessException {
        try {
            PersonInformation person = new PersonInformation();
            person.setPersonId(event.getPersonId());
            person.setName(event.getName());

            // we use saveAndFlush() to catch the DataAccessException.
            personInformationRepository.saveAndFlush(person);

            PersonSnapshot personSnapshot = new PersonSnapshot();
            personSnapshot.setId(UUID.randomUUID().toString());
            BeanUtils.copyProperties(event, personSnapshot);

            personSnapshotRepository.saveAndFlush(personSnapshot);

        } catch (DataAccessException ex) {
            throw new CheckedDataAccessException(String.format("DataAccessException occurred while persist event: { personId: %s, name: %s }", event.getPersonId(), event.getName()));
        }
    }
}
