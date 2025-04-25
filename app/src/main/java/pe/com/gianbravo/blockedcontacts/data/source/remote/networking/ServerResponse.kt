package pe.com.gianbravo.blockedcontacts.data.source.remote.networking

/**
 * @author Giancarlo Bravo Anlas
 *
 */
open class ServerResponse<T>(
	val status: Int,
	val data: T?,
	val message: String,
	val code: Int
) {
	val isProcessedSuccessfully
		get() = (1 == status)
}