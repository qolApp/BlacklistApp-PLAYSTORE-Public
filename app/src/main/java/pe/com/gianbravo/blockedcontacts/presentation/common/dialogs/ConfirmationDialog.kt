package pe.com.gianbravo.blockedcontacts.presentation.common.dialogs

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
fun ConfirmationDialog(
	message: String,
	@StringRes positiveButtonText: Int,
	@StringRes negativeButtonText: Int,
	onPositiveButtonClick: () -> Unit,
	onNegativeButtonClick: () -> Unit,
	modifier: Modifier = Modifier,
	@DrawableRes icon: Int? = null,
	title: String? = null
) {
	BasicAlertDialog(
		onDismissRequest = onNegativeButtonClick,
		modifier = modifier
	) {
		Surface(
			modifier = Modifier
				.wrapContentWidth()
				.wrapContentHeight(),
			shape = MaterialTheme.shapes.large
		) {
			Column {
				Box(
					modifier = Modifier
						.align(Alignment.CenterHorizontally)
						.padding(top = 8.dp)
				) {
					if (icon != null) {
						Image(
							painter = painterResource(id = icon),
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
					if (title != null) {
						Text(
							text = title,
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
						modifier = Modifier.padding(16.dp)
					)
				}
				Row(
					horizontalArrangement = Arrangement.SpaceBetween,
					modifier = Modifier.fillMaxWidth()
				) {
					Button(
						onClick = onNegativeButtonClick,
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
						Text(
							text = stringResource(id = negativeButtonText),
							style = IsicomTheme.dSTypography.bodyMD
						)
					}
					Button(
						onClick = onPositiveButtonClick,
						modifier = Modifier
							.weight(1f)
							.padding(start = 6.dp, top = 16.dp, end = 12.dp, bottom = 16.dp)
							.background(
								color = IsicomTheme.dSColor.brand.primary.dark,
								shape = RoundedCornerShape(size = 16.0f)
							)
					) {
						Text(
							text = stringResource(id = positiveButtonText),
							style = IsicomTheme.dSTypography.bodyMD
						)
					}
				}
			}
		}
	}
}

@Preview
@Composable
private fun ConfirmationDialogPreview() {
	IsicomTheme {
		ConfirmationDialog(
			title = stringResource(id = R.string.delete_item_title),
			icon = R.drawable.ic_alert_triangle_black,
			message = stringResource(id = R.string.delete_item_message),
			positiveButtonText = R.string.button_ok,
			negativeButtonText = R.string.button_cancel,
			onPositiveButtonClick = {},
			onNegativeButtonClick = {}
		)
	}
}

@Preview
@Composable
private fun ConfirmationDialogOnlyMessagePreview() {
	IsicomTheme {
		ConfirmationDialog(
			message = stringResource(id = R.string.delete_item_message),
			positiveButtonText = R.string.button_ok,
			negativeButtonText = R.string.button_cancel,
			onPositiveButtonClick = {},
			onNegativeButtonClick = {}
		)
	}
}
