package pe.com.gianbravo.blockedcontacts.data.source.remote

import android.net.Uri
import pe.com.gianbravo.blockedcontacts.data.CustomResult

/**
 * @author Giancarlo Bravo Anlas
 *
 */
interface IFileRepository {
	suspend fun uploadBlacklist(): CustomResult<String>
	suspend fun getBlacklistContacts(): CustomResult<Any>
	suspend fun importFromFile(uri: Uri?) : CustomResult<String>
	suspend fun exportToFile(uri: Uri?) : CustomResult<String>

}