package pe.com.gianbravo.blockedcontacts.presentation

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * @author Giancarlo Bravo Anlas
 *
 */

val presentationModule = module {
	viewModel {
		FileOptionsViewModel(get(), get())
	}

}