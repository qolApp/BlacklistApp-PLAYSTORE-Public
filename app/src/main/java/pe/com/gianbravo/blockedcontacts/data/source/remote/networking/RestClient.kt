package pe.com.gianbravo.blockedcontacts.data.source.remote.networking

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pe.com.gianbravo.blockedcontacts.BuildConfig
import pe.com.gianbravo.blockedcontacts.data.source.local.UserLocalDataSource
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author Giancarlo Bravo Anlas
 *
 */

object RestClient {

	private const val BASE_URL = "http://192.168.1.2:3000/"// BuildConfig.BASE_URL  // todo gba put correct urls
	private const val MAX_TIMEOUT = 10 // TODO GBA CHANGE FOR 60 SECS TIMEOUT

	@JvmStatic
	fun create(userLocalDataSource: UserLocalDataSource): Retrofit {
		val builder = OkHttpClient.Builder()

		builder.connectTimeout(MAX_TIMEOUT.toLong(), TimeUnit.SECONDS)
		builder.readTimeout(MAX_TIMEOUT.toLong(), TimeUnit.SECONDS)
		builder.writeTimeout(MAX_TIMEOUT.toLong(), TimeUnit.SECONDS)

		builder.addInterceptor { chain ->
			val request = chain.request().newBuilder()
				.addHeader("Content-Type", "application/json")
				.build()

			return@addInterceptor chain.proceed(request)
		}

		builder.addInterceptor { chain ->
			var request = chain.request()
			if (userLocalDataSource.isUserLoggedIn()) {
				request = request.newBuilder()
					.addHeader("Authorization", "${userLocalDataSource.accessToken}")
					.build()
			}

			return@addInterceptor chain.proceed(request)
		}

		val loggingInterceptor = HttpLoggingInterceptor()
		loggingInterceptor.level =
			if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.BASIC
		builder.addInterceptor(loggingInterceptor)

		return Retrofit.Builder()
			.addConverterFactory(GsonConverterFactory.create())
			.baseUrl(BASE_URL).client(builder.build()).build()
	}
}