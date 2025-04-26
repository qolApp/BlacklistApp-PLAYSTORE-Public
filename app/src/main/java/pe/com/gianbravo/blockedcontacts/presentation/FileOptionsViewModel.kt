package pe.com.gianbravo.blockedcontacts.presentation

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.saveable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pe.com.gianbravo.blockedcontacts.data.CustomResult
import pe.com.gianbravo.blockedcontacts.domain.interactor.FileInteractor
import javax.inject.Inject

/**
 * @author Giancarlo Bravo Anlas
 *
 */
class FileOptionsViewModel @Inject constructor(
	private val interactor: FileInteractor,
	private val savedState: SavedStateHandle
) : ViewModel() {
	var uiState: FileOptionUiState by savedState.saveable { mutableStateOf(value = FileOptionUiState.Idle) }
		private set

	fun exportRemoteBlacklist() {
		uiState = FileOptionUiState.Loading
		viewModelScope.launch {
			val result = withContext(Dispatchers.IO) {
				interactor.exportToServer()
			}
			uiState = when (result) {
				is CustomResult.Success -> {
					FileOptionUiState.SuccessId(result.data!!)
				}
				is CustomResult.Failure -> FileOptionUiState.Error(result.exception)
			}
		}

	}

	fun exportToFile(uri: Uri?, sendFile: (Uri?) -> Unit) {
		uiState = FileOptionUiState.Loading
		viewModelScope.launch {
			val result = withContext(Dispatchers.IO) {
				interactor.exportToFile(uri)
			}
			uiState = when (result) {
				is CustomResult.Success -> {
					sendFile(uri)
					FileOptionUiState.Success(result.data.toString())
				}
				is CustomResult.Failure -> FileOptionUiState.Error(result.exception)
			}
		}
	}

	fun importFromFile(uri: Uri?) {
		uiState = FileOptionUiState.Loading
		viewModelScope.launch {
			val result = withContext(Dispatchers.IO) {
				interactor.importFromFile(uri)
			}
			uiState = when (result) {
				is CustomResult.Success -> {
					FileOptionUiState.Success(result.data.toString())
				}
				is CustomResult.Failure -> FileOptionUiState.Error(result.exception)
			}
		}
	}


	fun importRemoteBlacklist() {
		uiState = FileOptionUiState.Loading
		viewModelScope.launch {
			val result = withContext(Dispatchers.IO) {
				interactor.importFromServer()
			}
			uiState = when (result) {
				is CustomResult.Success -> {
					FileOptionUiState.SuccessId(result.data!!)
				}
				is CustomResult.Failure -> FileOptionUiState.Error(result.exception)
			}
		}
	}

}