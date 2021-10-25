package info.cepheus.showcase_axon_exception.config;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.*;

@Slf4j
public class MyAxonErrorHandler implements ErrorHandler, ListenerInvocationErrorHandler {

    @Override
    public void handleError(ErrorContext errorContext) throws Exception {
        log.error(errorContext.error().getMessage(), errorContext.error());
        Throwable error = errorContext.error();
        if (error instanceof Error) {
            throw (Error) error;
        } else if (error instanceof Exception) {
            throw (Exception) error;
        } else {
            throw new EventProcessingException("An error occurred while handling an event", error);
        }
    }

    @Override
    public void onError(Exception exception, EventMessage<?> event, EventMessageHandler eventHandler) throws Exception {
        log.error(exception.getMessage(), exception);
        throw exception;
    }
}
