package pe.com.gianbravo.blockedcontacts.data.source.remote.datasource

import okhttp3.MultipartBody
import pe.com.gianbravo.blockedcontacts.data.source.remote.api.FileApi
import retrofit2.Retrofit

/**
 * @author Giancarlo Bravo Anlas
 *
 */
class FileRemoteDataSource(retrofit: Retrofit) : FileApi {
	private val api by lazy { retrofit.create(FileApi::class.java) }
	override suspend fun uploadBlacklist(file: MultipartBody.Part?) = api.uploadBlacklist(file)
	override suspend fun getBlacklistContacts() = api.getBlacklistContacts()
}