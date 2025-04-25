package pe.com.gianbravo.blockedcontacts.data.source.remote.networking

import com.google.gson.annotations.SerializedName

/**
 * @author Giancarlo Bravo Anlas
 *
 */

data class BlackListData(
	@SerializedName("count") val count: Int,
	@SerializedName("list") val list: List<String>,
)

typealias BlacklistServerResponse = ServerResponse<BlackListData>
typealias BlankServerResponse = ServerResponse<Nothing>


// Error
class ErrorResponse(status: Int, message: String, code: Int) :
	ServerResponse<Nothing>(status, null, message, code)
