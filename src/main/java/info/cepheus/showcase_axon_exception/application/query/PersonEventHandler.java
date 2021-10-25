package info.cepheus.showcase_axon_exception.application.query;

import info.cepheus.showcase_axon_exception.coreapi.event.PersonCreatedEvent;
import info.cepheus.showcase_axon_exception.coreapi.exception.CheckedDataAccessException;
import info.cepheus.showcase_axon_exception.coreapi.exception.NullOrEmptyNameException;
import info.cepheus.showcase_axon_exception.domain.query.personInformation.PersonInformation;
import info.cepheus.showcase_axon_exception.domain.query.personInformation.PersonInformationRepository;
import info.cepheus.showcase_axon_exception.domain.query.personSnapshot.PersonSnapshot;
import info.cepheus.showcase_axon_exception.domain.query.personSnapshot.PersonSnapshotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class PersonEventHandler {

    private final PersonInformationRepository personInformationRepository;

    private final PersonQueryService personQueryService;

    @EventHandler
    public void on(PersonCreatedEvent event) throws NullOrEmptyNameException, CheckedDataAccessException {
        // actually we should check this validity in the command handler,
        // but in an event-sourcing based system, the "problematic historical events" should be handled as a fact properly,
        // so we added this check here as well
        if (ObjectUtils.isEmpty(event.getName())) {
            throw new NullOrEmptyNameException(String.format("event { personId: %s }", event.getPersonId()));
        }

        personQueryService.save(event);
    }

    /**
     * 1. if we use the default behavior, we can safely forget any Checked-Exception thrown inside EventHandler,
     * without any ExceptionHandler.
     * EventHandler will not be retried because the default implementation is "LoggingErrorHandler".
     * 2. if we use MyAxonErrorHandler which is like "PropagatingErrorHandler",
     * Axon will not retry if we do not reThrow this exception after handle it in @ExceptionHandler.
     */
    @ExceptionHandler(resultType = NullOrEmptyNameException.class)
    public void handle(NullOrEmptyNameException exception) throws Exception {
        log.error("NullOrEmptyNameException handled", exception);
//        throw exception;
    }

    /**
     * Axon will still enter Retry mode, even if we handled this type of exception here.
     */
    @ExceptionHandler(resultType = CheckedDataAccessException.class)
    public void handle(CheckedDataAccessException exception) {
        log.error("CheckedDataAccessException handled", exception);
    }

}
