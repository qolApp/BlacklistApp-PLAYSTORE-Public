package pe.com.gianbravo.blockedcontacts.data.source.remote

import pe.com.gianbravo.blockedcontacts.data.CustomResult
import pe.com.gianbravo.blockedcontacts.data.source.remote.networking.BlackListData

/**
 * @author Giancarlo Bravo Anlas
 *
 */
interface IFileRepository {
	suspend fun uploadBlacklist(): CustomResult<String>
	suspend fun getBlacklistContacts(): CustomResult<BlackListData>
}