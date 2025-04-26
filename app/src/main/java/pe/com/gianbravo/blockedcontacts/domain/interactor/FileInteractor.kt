package pe.com.gianbravo.blockedcontacts.domain.interactor

import android.net.Uri
import pe.com.gianbravo.blockedcontacts.R
import pe.com.gianbravo.blockedcontacts.data.CustomResult
import pe.com.gianbravo.blockedcontacts.data.source.remote.IFileRepository

/**
 * @author Giancarlo Bravo Anlas
 *
 */
class FileInteractor(
	private val fileRepository: IFileRepository,
) {

	suspend fun exportToFile(uri: Uri?): CustomResult<String> {
		return fileRepository.exportToFile(uri)
	}

	suspend fun exportToServer(): CustomResult<Int> {
		return when (val result = fileRepository.uploadBlacklist()) {
			is CustomResult.Success -> {
				CustomResult.Success(R.string.text_remote_export_success)
			}
			is CustomResult.Failure -> {
				// if it fails, try on the local db
				result
			}
		}
	}

	suspend fun importFromFile(uri: Uri?): CustomResult<String> {
		return fileRepository.importFromFile(uri)
	}

	suspend fun importFromServer(): CustomResult<Int> {
		return when (val result = fileRepository.getBlacklistContacts()) {
			is CustomResult.Success -> {
				//fileRepository.addBlockedNumbers(context, result.data)
				CustomResult.Success(R.string.text_remote_import_success)
			}
			is CustomResult.Failure -> {
				// if it fails, try on the local db
				result
			}
		}
	}
}