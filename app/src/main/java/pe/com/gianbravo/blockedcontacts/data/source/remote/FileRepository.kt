package pe.com.gianbravo.blockedcontacts.data.source.remote

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.apache.commons.io.FileUtils
import pe.com.gianbravo.blockedcontacts.data.CustomResult
import pe.com.gianbravo.blockedcontacts.data.source.remote.api.FileApi
import pe.com.gianbravo.blockedcontacts.data.source.remote.networking.BlackListData
import pe.com.gianbravo.blockedcontacts.data.source.remote.networking.NetworkHandler
import pe.com.gianbravo.blockedcontacts.domain.BlacklistContacts
import pe.com.gianbravo.blockedcontacts.presentation.BlockedNumbersFragment
import pe.com.gianbravo.blockedcontacts.utils.Constants.FILE_NAME
import timber.log.Timber
import java.io.File
import java.io.IOException

/**
 * @author Giancarlo Bravo Anlas
 *
 */
class FileRepository
constructor(
	private val context: Context,
	private val networkHandler: NetworkHandler,
	private val source: FileApi
) : IFileRepository {


	override suspend fun uploadBlacklist(): CustomResult<String> {

		val parts = mutableListOf<MultipartBody.Part>()
		lateinit var outputJson: String
		val list = BlockedNumbersFragment.getBlocked(context)
		val data = BlacklistContacts(
			list.size,
			list
		)
		val gson = Gson()
		outputJson = gson.toJson(data)

		val file = File(context.filesDir, FILE_NAME)
		//writeFile(context, outputJson, uri)
		FileUtils.writeStringToFile(file, outputJson, "UTF-8")

		var part :MultipartBody.Part? = null
		val tempFile = createTempFileFromUri(context, file.toUri(), "temp_", ".json")
		if (tempFile != null) {
			val requestBody = tempFile.asRequestBody("text/plain".toMediaTypeOrNull())
			part = MultipartBody.Part.createFormData("file", tempFile.name, requestBody)
			Timber.d(tempFile.name)
			parts.add(part)
		}
		//val agent = RequestBody.create(MediaType.parse("text/plain"), "<agent-value>");

		val result = networkHandler.handleServerResponse {
			source.uploadBlacklist(part)
		}
		return when (result) {
			is CustomResult.Success -> {
				CustomResult.Success(result.data)
			}
			is CustomResult.Failure -> result
		}
	}


	fun createTempFileFromUri(context: Context, uri: Uri?, fileName: String?, suffix: String?): File? {
		try {
			context.contentResolver.openInputStream(uri!!).use { stream ->
				val file = File.createTempFile(fileName, suffix, context.cacheDir)
				FileUtils.copyInputStreamToFile(stream, file)
				return file
			}
		}
		catch (e: IOException) {
			return null
		}
	}

	override suspend fun getBlacklistContacts(): CustomResult<BlackListData> {
		val result = networkHandler.handleServerResponse {
			source.getBlacklistContacts()
		}
		return when (result) {
			is CustomResult.Success -> {
				CustomResult.Success(result.data)
			}
			is CustomResult.Failure -> result
		}
	}

}