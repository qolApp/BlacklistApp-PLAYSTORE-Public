package pe.com.gianbravo.blockedcontacts.presentation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @author Giancarlo Bravo Anlas
 *
 */
@Parcelize
sealed class FileOptionUiState : Parcelable {
	data object Idle : FileOptionUiState()
	data object Loading : FileOptionUiState()
	data class Success(val message: String) : FileOptionUiState()
	data class SuccessId(val messageId: Int) : FileOptionUiState()
	data class Error(val exception: Throwable) : FileOptionUiState()
	data object Done : FileOptionUiState()
}

val FileOptionUiState.message: String
	get() = (this as FileOptionUiState.Success).message

val FileOptionUiState.exception: Throwable
	get() = (this as FileOptionUiState.Error).exception