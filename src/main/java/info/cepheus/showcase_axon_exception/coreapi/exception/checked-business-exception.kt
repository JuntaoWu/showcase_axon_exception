package info.cepheus.showcase_axon_exception.coreapi.exception

import org.axonframework.common.AxonNonTransientException

enum class CheckedBusinessErrorCode {
    NULL_OR_EMPTY_NAME,
    UNKNOWN
}

data class CheckedBusinessError(
        val name: String,
        val code: CheckedBusinessErrorCode,
        val message: String
)

// CheckedBusinessException is non-transient and should not be treated as RuntimeException
open class CheckedBusinessException(
        private val errorMessage: String,
        val errorCode: CheckedBusinessErrorCode
) : Exception(errorMessage)

data class NullOrEmptyNameException(val reason: String?) : CheckedBusinessException(
        String.format("Given %s name is null/empty", reason),
        CheckedBusinessErrorCode.NULL_OR_EMPTY_NAME
)