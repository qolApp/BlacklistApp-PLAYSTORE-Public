package pe.com.gianbravo.blockedcontacts.data.source.remote.api

import okhttp3.MultipartBody
import pe.com.gianbravo.blockedcontacts.BuildConfig
import pe.com.gianbravo.blockedcontacts.data.source.remote.networking.BlacklistServerResponse
import pe.com.gianbravo.blockedcontacts.data.source.remote.networking.BlankServerResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

/**
 * @author Giancarlo Bravo Anlas
 *
 */
interface FileApi {

	@GET(BuildConfig.ENV + "/api/file/load")
	suspend fun getBlacklistContacts(
	): Response<BlacklistServerResponse>

	@Multipart
	@POST(BuildConfig.ENV + "/api/file/upload")
	suspend fun uploadBlacklist(
		@Part file: MultipartBody.Part?,
	): Response<BlankServerResponse>

}