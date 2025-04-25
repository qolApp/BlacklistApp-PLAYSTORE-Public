package pe.com.gianbravo.blockedcontacts.data.source.remote.repository

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import pe.com.gianbravo.blockedcontacts.data.source.remote.FileRepository
import pe.com.gianbravo.blockedcontacts.data.source.remote.IFileRepository
import pe.com.gianbravo.blockedcontacts.data.source.remote.api.FileApi
import pe.com.gianbravo.blockedcontacts.data.source.remote.datasource.FileRemoteDataSource
import pe.com.gianbravo.blockedcontacts.data.source.remote.networking.NetworkHandler
import pe.com.gianbravo.blockedcontacts.data.source.remote.networking.RestClient

/**
 * @author Giancarlo Bravo Anlas
 *
 */

val repositoryModule = module {
	single { RestClient.create(get()) }
	single { NetworkHandler() }

	factory { FileRepository(androidContext(),get() , get ()) as IFileRepository }


	single { FileRemoteDataSource(get()) as FileApi }

}