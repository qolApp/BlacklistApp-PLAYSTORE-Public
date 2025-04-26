package pe.com.gianbravo.blockedcontacts.presentation.common.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import pe.com.gianbravo.blockedcontacts.presentation.common.theme.IsicomTheme

/**
 * @author Giancarlo Bravo Anlas
 *
 */
@Composable
fun ProgressDialog() {
	Dialog(
		onDismissRequest = {},
		properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
	) {
		Box(
			contentAlignment = Alignment.Center,
			modifier = Modifier
				.size(size = 80.dp)
				.background(color = Color.White, shape = RoundedCornerShape(size = 10.dp))
		) {
			CircularProgressIndicator(color = IsicomTheme.dSColor.brand.primary.dark)
		}
	}
}

@Preview
@Composable
private fun ProgressDialogPreview() = IsicomTheme { ProgressDialog() }
