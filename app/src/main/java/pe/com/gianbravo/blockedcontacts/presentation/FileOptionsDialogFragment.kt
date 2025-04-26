package pe.com.gianbravo.blockedcontacts.presentation

import android.content.Intent
import android.net.Uri
import android.os.StrictMode
import androidx.compose.runtime.Composable
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import pe.com.gianbravo.blockedcontacts.R
import pe.com.gianbravo.blockedcontacts.presentation.common.base.ComposeDialogFragment
import pe.com.gianbravo.blockedcontacts.presentation.common.theme.IsicomTheme
import pe.com.gianbravo.blockedcontacts.utils.Constants.fileType

/**
 * @author Giancarlo Bravo Anlas
 *
 */
class FileOptionsDialogFragment : ComposeDialogFragment() {
	private val args by navArgs<FileOptionsDialogFragmentArgs>()
	private val viewModel: FileOptionsViewModel by viewModel()

	@Composable
	override fun View() {
		IsicomTheme {
			FileOptionsScreen(
				isImportFunctionality = args.isImport,
				viewModel = viewModel,
				onSuccess = { findNavController().popBackStack() },
				onBack = { findNavController().popBackStack() },
				sendFile = ::sendFile
			)
		}
	}

	fun sendFile(uri: Uri?){
		val builder = StrictMode.VmPolicy.Builder()
		StrictMode.setVmPolicy(builder.build())
		val intentShareFile = Intent(Intent.ACTION_SEND)

		// share via email
		intentShareFile.type = fileType
		intentShareFile.putExtra(
			Intent.EXTRA_STREAM,
			uri
		)
		intentShareFile.putExtra(
			Intent.EXTRA_SUBJECT,
			getString(R.string.text_share_subject)
		)
		intentShareFile.putExtra(
			Intent.EXTRA_TEXT,
			getString(R.string.text_share_text)
		)
		startActivity(Intent.createChooser(intentShareFile, "Share File"))
	}
}
