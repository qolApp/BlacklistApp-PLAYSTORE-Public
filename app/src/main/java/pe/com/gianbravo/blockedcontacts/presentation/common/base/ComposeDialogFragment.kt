package pe.com.gianbravo.blockedcontacts.presentation.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.fragment.app.DialogFragment
import androidx.fragment.compose.content

/**
 * @author Giancarlo Bravo Anlas
 *
 */
abstract class ComposeDialogFragment : DialogFragment() {

	@Composable
	protected abstract fun View()

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	) = content { View() }

	override fun onStart() {
		super.onStart()
		requireDialog().window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
		requireDialog().window?.setBackgroundDrawableResource(android.R.color.transparent)
	}
}