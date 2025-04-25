package pe.com.gianbravo.blockedcontacts.data.source.remote.networking

import com.google.gson.Gson
import pe.com.gianbravo.blockedcontacts.data.CustomResult
import pe.com.gianbravo.blockedcontacts.data.NetworkException
import pe.com.gianbravo.blockedcontacts.data.ServerException
import pe.com.gianbravo.blockedcontacts.data.UnauthorizedException
import retrofit2.Response
import java.io.IOException
import java.lang.Exception

/**
 * @author Giancarlo Bravo Anlas
 *
 */

class NetworkHandler {

	companion object {
		const val NETWORK_CONNECTION_ERROR =
			"Por favor verifique su conexión a internet e inténtelo nuevamente."
		const val DEFAULT_ERROR = "Ha ocurrido un problema con el servidor, por favor inténtelo más tarde."
		const val UNAUTHORIZED_ERROR = "Su sesión ha expirado. Por favor vuelva a iniciar sesión."
	}

	val defaultErrorResult: CustomResult.Failure
		get() = CustomResult.Failure(ServerException(DEFAULT_ERROR))

	inline fun <T> handleServerResponse(
		task: () -> Response<ServerResponse<T>>
	): CustomResult<T> {
		try {
			val response = task()
			if (response.isSuccessful) {
				val body = response.body()
				if (body != null) {
					if (body.isProcessedSuccessfully) {
						return CustomResult.Success(body.data)
					}
					if (body.code == 401) return CustomResult.Failure(
						UnauthorizedException(
							if (body.message.isNotBlank()) body.message else UNAUTHORIZED_ERROR
						)
					)
					if (body.message.isNotBlank()) {
						return CustomResult.Failure(ServerException(body.message))
					}
				}
			}
			else {
				val errorResponseAsString = response.errorBody()?.string()
				val errorResponse =
					Gson().fromJson(errorResponseAsString, ErrorResponse::class.java)

				if (errorResponse != null) {
					if (errorResponse.code == 401) {
						return CustomResult.Failure(
							UnauthorizedException(
								if (errorResponse.message.isNotBlank()) errorResponse.message else UNAUTHORIZED_ERROR
							)
						)
					}
					if (errorResponse.message.isNotBlank()) {
						return CustomResult.Failure(ServerException(errorResponse.message))
					}
				}
				else {
					if (response.code() == 401) {
						return CustomResult.Failure(UnauthorizedException(UNAUTHORIZED_ERROR))
					}
				}
			}

			return defaultErrorResult
		}
		catch (e: Exception) {
			e.printStackTrace()

			if (e is IOException) {
				return CustomResult.Failure(NetworkException(NETWORK_CONNECTION_ERROR))
			}

			return defaultErrorResult
		}
	}
}