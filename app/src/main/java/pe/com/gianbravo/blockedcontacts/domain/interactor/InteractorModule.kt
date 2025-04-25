package pe.com.gianbravo.blockedcontacts.domain.interactor

import org.koin.dsl.module

/**
 * @author Giancarlo Bravo Anlas
 *
 */
val interactorModule = module {
	factory { FileInteractor(get() ) }
}