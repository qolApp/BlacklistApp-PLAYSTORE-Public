package pe.com.gianbravo.blockedcontacts.data

/**
 * @author Giancarlo Bravo Anlas
 *
 */
sealed class CustomResult<out T> {
	data class Success<out T>(val data: T?) : CustomResult<T>()
	data class Failure(val exception: Exception) : CustomResult<Nothing>()

	suspend fun process(fnL: suspend (T?) -> Any, fnR: (Failure) -> Any): Any =
		when (this) {
			is Success -> fnL(data)
			is Failure -> fnR(Failure(exception))
		}

	companion object {
		fun customError(message: String): Failure {
			return Failure(Exception(message))
		}
	}
}

class NonNullDataExpectedException(message: String?) : Exception(message)
class NetworkException(message: String?) : Exception(message)
class UnauthorizedException(message: String?) : Exception(message)
class ServerException(message: String?) : Exception(message)