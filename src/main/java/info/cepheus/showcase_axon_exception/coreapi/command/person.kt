package info.cepheus.showcase_axon_exception.coreapi.command

import org.axonframework.modelling.command.TargetAggregateIdentifier


data class CreatePersonCommand(
        @TargetAggregateIdentifier
        var personId: String,
        var name: String
)
