package pe.com.gianbravo.blockedcontacts

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import pe.com.gianbravo.blockedcontacts.data.source.remote.repository.repositoryModule
import pe.com.gianbravo.blockedcontacts.domain.interactor.interactorModule
import pe.com.gianbravo.blockedcontacts.presentation.presentationModule

import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        startKoin {
            androidContext(this@App)
            modules(
                dataModule,
                interactorModule,
                repositoryModule,
                presentationModule
            )
        }
    }
}
