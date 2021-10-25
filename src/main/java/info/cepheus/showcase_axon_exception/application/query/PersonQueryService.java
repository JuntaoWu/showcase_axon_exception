package info.cepheus.showcase_axon_exception.application.query;

import info.cepheus.showcase_axon_exception.coreapi.event.PersonCreatedEvent;
import info.cepheus.showcase_axon_exception.coreapi.exception.CheckedDataAccessException;

public interface PersonQueryService {
    void save(PersonCreatedEvent event) throws CheckedDataAccessException;
}
