package info.cepheus.showcase_axon_exception.coreapi.exception

open class CheckedDataAccessException(
        private val errorMessage: String
) : Exception(errorMessage)