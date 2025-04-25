package pe.com.gianbravo.blockedcontacts.domain.interactor

import pe.com.gianbravo.blockedcontacts.data.CustomResult
import pe.com.gianbravo.blockedcontacts.data.source.remote.IFileRepository

/**
 * @author Giancarlo Bravo Anlas
 *
 */
class FileInteractor(
	private val fileRepository: IFileRepository,
) {
	suspend fun uploadBlacklist(): CustomResult<String> {
		return fileRepository.uploadBlacklist()
	}

	suspend fun getBlacklistContacts(): CustomResult<List<String>> {
		return when (val result = fileRepository.getBlacklistContacts()) {
			is CustomResult.Success -> {
				CustomResult.Success(result.data?.list)
			}
			is CustomResult.Failure -> {
				// if it fails, try on the local db
				result
			}
		}
	}
}