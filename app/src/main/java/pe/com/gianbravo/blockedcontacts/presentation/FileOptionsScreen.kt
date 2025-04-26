package pe.com.gianbravo.blockedcontacts.presentation

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import pe.com.gianbravo.blockedcontacts.R
import pe.com.gianbravo.blockedcontacts.presentation.common.dialogs.ErrorServiceDialog
import pe.com.gianbravo.blockedcontacts.presentation.common.dialogs.InformationDialog
import pe.com.gianbravo.blockedcontacts.presentation.common.dialogs.ProgressDialog
import pe.com.gianbravo.blockedcontacts.presentation.common.parameter.BooleanParameterProvider
import pe.com.gianbravo.blockedcontacts.presentation.common.theme.IsicomTheme
import pe.com.gianbravo.blockedcontacts.utils.Constants.FILE_NAME
import pe.com.gianbravo.blockedcontacts.utils.Constants.fileType

/**
 * @author Giancarlo Bravo Anlas
 *
 */
@Composable
fun FileOptionsScreen(
	onSuccess: () -> Unit,
	viewModel: FileOptionsViewModel,
	isImportFunctionality: Boolean = true,
	onBack: (Boolean) -> Unit,
	sendFile: (Uri?) -> Unit
) {
	LaunchedEffect(Unit) {
		//viewModel.setupItem(scheduleItem)
	}

	val openDocLauncher = rememberLauncherForActivityResult(
		contract = ActivityResultContracts.OpenDocument(),
		onResult = { uri ->
			viewModel.importFromFile(uri)
		}
	)
	val createDocLauncher = rememberLauncherForActivityResult(
		contract = ActivityResultContracts.CreateDocument(fileType),
		onResult = { uri ->
			viewModel.exportToFile(uri, sendFile)
		}
	)


	fun importBlacklist() {
		openDocLauncher.launch(arrayOf(fileType))
	}

	fun exportBlacklist() {
		createDocLauncher.launch(FILE_NAME)
	}


	BackHandler {
		onBack(true)
	}

	ItemRegistrationScreen(
		isImportFunctionality = isImportFunctionality,
		onLocalButtonClick = {
			if (isImportFunctionality) importBlacklist() else exportBlacklist()
		},
		onRemoteButtonClick = {
			if (isImportFunctionality) viewModel.importRemoteBlacklist() else viewModel.exportRemoteBlacklist()},
	)

	when (viewModel.uiState) {
		FileOptionUiState.Idle, FileOptionUiState.Done -> {}
		FileOptionUiState.Loading -> ProgressDialog()
		is FileOptionUiState.Success -> InformationDialog(
			icon = R.drawable.ic_check_circle_success,
			title = stringResource(id = R.string.close_dialog),
			message = (viewModel.uiState as FileOptionUiState.Success).message,
			onDismiss = onSuccess
		)
		is FileOptionUiState.SuccessId -> InformationDialog(
			icon = R.drawable.ic_check_circle_success,
			title = stringResource(id = R.string.close_dialog),
			message = stringResource(id = (viewModel.uiState as FileOptionUiState.SuccessId).messageId),
			onDismiss = onSuccess
		)
		is FileOptionUiState.Error ->
			//if ((viewModel.uiState as FileOptionUiState.Error).exception.message == Constants.ERROR_INVALID_TOKEN) {
			//onCloseSession()
			//}
			//else {
			ErrorServiceDialog(
				message = (viewModel.uiState as FileOptionUiState.Error).exception.message,
				onDismiss = {onBack(false)}
			)
		//}
	}
}

@Composable
fun ItemRegistrationScreen(
	isImportFunctionality: Boolean = true,
	onLocalButtonClick: ()-> Unit ={},
	onRemoteButtonClick: ()-> Unit ={},
) {
	Column(
		modifier = Modifier.verticalScroll(state = rememberScrollState()),
		horizontalAlignment = Alignment.CenterHorizontally
	) {

		Row(
			horizontalArrangement = Arrangement.SpaceBetween,
			modifier = Modifier.fillMaxWidth()
		) {
			Button(
				onClick = onLocalButtonClick,
				colors = ButtonDefaults.buttonColors(
					containerColor = IsicomTheme.dSColor.neutral.dark,
					contentColor = Color.White
				),
				modifier = Modifier
					.weight(1f)
					.padding(start = 12.dp, top = 16.dp, end = 6.dp, bottom = 16.dp)
					.background(
						color = IsicomTheme.dSColor.neutral.dark,
						shape = RoundedCornerShape(size = 16.0f)
					)
			) {
				val negativeButtonText = if (isImportFunctionality) R.string.text_import else R.string.text_export
				Text(
					text = stringResource(id = negativeButtonText),
					style = IsicomTheme.dSTypography.bodyMD
				)
			}
			Button(
				onClick = onRemoteButtonClick,
				modifier = Modifier
					.weight(1f)
					.padding(start = 6.dp, top = 16.dp, end = 12.dp, bottom = 16.dp)
					.background(
						color = IsicomTheme.dSColor.brand.primary.dark,
						shape = RoundedCornerShape(size = 16.0f)
					)
			) {
				val positiveButtonText = if (isImportFunctionality) R.string.text_remote_import else R.string.text_export_remote
				Text(
					text = stringResource(id = positiveButtonText),
					style = IsicomTheme.dSTypography.bodyMD
				)
			}
		}

	}
}

@Preview
@Composable
fun ItemRegistrationScreenPreview(
	@PreviewParameter(BooleanParameterProvider::class) isMonthlyPayment: Boolean
) {
	//val uiData = ItemRegistrationUiData(
	//isMonthlyPayment = isMonthlyPayment
	//)
	//val uiActions = ItemRegistrationUiActionsImpl()
	IsicomTheme {
		ItemRegistrationScreen(
			isImportFunctionality = false
		)
	}
}
