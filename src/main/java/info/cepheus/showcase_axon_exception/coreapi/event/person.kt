package info.cepheus.showcase_axon_exception.coreapi.event

data class PersonCreatedEvent(
        var personId: String,
        var name: String
)