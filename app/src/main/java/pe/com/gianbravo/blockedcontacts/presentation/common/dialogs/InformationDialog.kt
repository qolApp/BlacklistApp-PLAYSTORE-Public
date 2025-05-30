package pe.com.gianbravo.blockedcontacts.presentation.common.dialogs

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pe.com.gianbravo.blockedcontacts.R
import pe.com.gianbravo.blockedcontacts.presentation.common.theme.IsicomTheme

/**
 * @author Giancarlo Bravo Anlas
 *
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InformationDialog(
	message: String,
	onDismiss: () -> Unit,
	modifier: Modifier = Modifier,
	@DrawableRes icon: Int? = null,
	title: String? = null,
) {
	BasicAlertDialog(
		onDismissRequest = onDismiss,
		modifier = modifier
	) {
		Surface(
			shape = MaterialTheme.shapes.large
		) {
			Column {
				Box(
					modifier = Modifier.align(Alignment.End)
				) {
					Image(
						painter = painterResource(id = R.drawable.ic_close),
						contentDescription = stringResource(R.string.close_dialog),
						modifier = Modifier
                         .size(40.dp)
                         .padding(8.dp)
                         .clickable(onClick = onDismiss)
					)
				}
				Box(
					modifier = Modifier.align(Alignment.CenterHorizontally)
				) {
					icon?.let {
						Image(
							painter = painterResource(id = it),
							contentDescription = null,
							modifier = Modifier
                             .size(64.dp)
                             .padding(8.dp)
						)
					}
				}
				Box(
					modifier = Modifier.align(Alignment.CenterHorizontally)
				) {
					title?.let {
						Text(
							text = it,
							style = IsicomTheme.dSTypography.titleXS,
							textAlign = TextAlign.Center,
							color = IsicomTheme.dSColor.neutral.dark,
							modifier = Modifier.padding(8.dp)
						)
					}
				}
				Box(
					modifier = Modifier.align(Alignment.CenterHorizontally)
				) {
					Text(
						text = message,
						style = IsicomTheme.dSTypography.bodyMD,
						textAlign = TextAlign.Center,
						color = IsicomTheme.dSColor.neutral.dark,
						modifier = Modifier.padding(
							start = 16.dp,
							end = 16.dp,
							top = if (title == null) 0.dp else 8.dp,
							bottom = 16.dp
						)
					)
				}
			}
		}
	}
}

@Preview
@Composable
private fun InformationDialogPreview() {
	IsicomTheme {
		InformationDialog(
			icon = R.drawable.ic_check_circle_success,
			title = stringResource(id = R.string.close_dialog),
			message = "Se ha creado correctamente el DM 0000836350 en la Sede.",
			onDismiss = {}
		)
	}
}

@Preview
@Composable
private fun InformationDialogOnlyMessagePreview() {
	IsicomTheme {
		InformationDialog(
			message = stringResource(id = R.string.close_dialog),
			onDismiss = {}
		)
	}
}
