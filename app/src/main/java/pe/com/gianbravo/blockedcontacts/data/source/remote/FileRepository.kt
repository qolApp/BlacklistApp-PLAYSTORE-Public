package pe.com.gianbravo.blockedcontacts.data.source.remote

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.BlockedNumberContract
import androidx.core.net.toUri
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.apache.commons.io.FileUtils
import pe.com.gianbravo.blockedcontacts.R
import pe.com.gianbravo.blockedcontacts.data.CustomResult
import pe.com.gianbravo.blockedcontacts.data.source.remote.api.FileApi
import pe.com.gianbravo.blockedcontacts.data.source.remote.networking.NetworkHandler
import pe.com.gianbravo.blockedcontacts.domain.BlacklistContacts
import pe.com.gianbravo.blockedcontacts.presentation.BlockedNumbersFragment.Companion.putNumberOnBlocked
import pe.com.gianbravo.blockedcontacts.utils.Constants.FILE_NAME
import pe.com.gianbravo.blockedcontacts.utils.FileUtils.Companion.writeFile
import timber.log.Timber
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader

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
		val list = getBlocked(context)
		val data = BlacklistContacts(
			list.size,
			list
		)
		val gson = Gson()
		outputJson = gson.toJson(data)

		val file = File(context.filesDir, FILE_NAME)
		//writeFile(context, outputJson, uri)
		FileUtils.writeStringToFile(file, outputJson, "UTF-8")

		var part: MultipartBody.Part? = null
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

	override suspend fun getBlacklistContacts(): CustomResult<Any> {
		val result = networkHandler.handleServerResponse {
			source.getBlacklistContacts()
		}
		return when (result) {
			is CustomResult.Success -> {
				addBlockedNumbers(context, result.data?.list)
				CustomResult.Success("")
			}
			is CustomResult.Failure -> result
		}
	}

	override suspend fun importFromFile(uri: Uri?): CustomResult<String> {
		lateinit var stringBuilder: StringBuilder
		var fileFound: Boolean
		if (uri != null) {
			//read file
			try {
				val fileInputStream = BufferedInputStream(
					context.contentResolver.openInputStream(uri)
				)
				val inputStreamReader = InputStreamReader(fileInputStream)
				val bufferedReader = BufferedReader(inputStreamReader)
				stringBuilder = StringBuilder()
				var text: String? = null
				while ({ text = bufferedReader.readLine(); text }() != null) {
					stringBuilder.append(text)
				}
				fileInputStream.close()
				fileFound = true
			}
			catch (e: Exception) {
				// Notify User of fail
				fileFound = false
				Timber.tag("FileRepository").d(e)
			}
			if (fileFound) {
				// parse to object
				val blacklistContacts: BlacklistContacts =
					Gson().fromJson(stringBuilder.toString(), BlacklistContacts::class.java)

				// load numbers to blacklist
				addBlockedNumbers(context, blacklistContacts.list)
				return CustomResult.Success(context.getString(R.string.text_added_numbers))
			}
			else
				return CustomResult.customError(context.getString(R.string.error_read_file))
		}
		else {
			return CustomResult.customError(context.getString(R.string.error_file))
		}
	}

	override suspend fun exportToFile(uri: Uri?): CustomResult<String> {
		if (uri != null) {
			// Get the data
			lateinit var outputJson: String
			val list = getBlocked(context)
			val data = BlacklistContacts(
				list.size,
				list
			)
			val gson = Gson()
			outputJson = gson.toJson(data)
			// Save the data into the selected file
			writeFile(context, outputJson, uri)

			return CustomResult.Success(context.getString(R.string.text_export_success))
		}
		else {
			return CustomResult.customError(context.getString(R.string.error_file))
		}
	}

	fun addBlockedNumbers(context: Context?, list: List<String>?) {
		list?.forEach {
			putNumberOnBlocked(
				number = it, isFromMultiple = true, context = context, onSuccess = {
				}
			)
		}
	}

	fun getBlocked(context: Context?): ArrayList<String> {
		val list = arrayListOf<String>()
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			context?.contentResolver?.let {
				val record: Cursor? =
					it.query(
						BlockedNumberContract.BlockedNumbers.CONTENT_URI, arrayOf(
							BlockedNumberContract.BlockedNumbers.COLUMN_ID,
							BlockedNumberContract.BlockedNumbers.COLUMN_ORIGINAL_NUMBER,
							BlockedNumberContract.BlockedNumbers.COLUMN_E164_NUMBER
						), null, null, null
					)

				if (record != null && record.count != 0) {
					if (record.moveToFirst()) {
						do {
							val blockNumber =
								record.getString(record.getColumnIndex(BlockedNumberContract.BlockedNumbers.COLUMN_ORIGINAL_NUMBER))
							list.add(blockNumber)
						} while (record.moveToNext())
					}
					record.close()
				}
			}
		}
		list.sort()
		return list
	}


}