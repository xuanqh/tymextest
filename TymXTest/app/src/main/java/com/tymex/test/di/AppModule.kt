package com.tymex.test.di

import android.app.Application
import android.content.Context
import com.tymex.test.R
import com.tymex.test.data.remote.ServiceApi
import com.tymex.test.data.repository.UserRepositoryImpl
import com.tymex.test.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.InputStream
import java.security.KeyStore
import java.security.cert.CertificateFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(context: Context): OkHttpClient {
        try {
            val cf = CertificateFactory.getInstance("X.509")
            val cert: InputStream = context.resources.openRawResource(R.raw.github_cert)
            val ca = cf.generateCertificate(cert)
            cert.close()

            val keyStore = KeyStore.getInstance(KeyStore.getDefaultType()).apply {
                load(null, null)
                setCertificateEntry("ca", ca)
            }

            val trustManagerFactory = javax.net.ssl.TrustManagerFactory.getInstance(
                javax.net.ssl.TrustManagerFactory.getDefaultAlgorithm()
            ).apply {
                init(keyStore)
            }

            return OkHttpClient.Builder()
                .sslSocketFactory(
                    javax.net.ssl.SSLContext.getInstance("TLS").apply {
                        init(null, trustManagerFactory.trustManagers, java.security.SecureRandom())
                    }.socketFactory,
                    trustManagerFactory.trustManagers[0] as javax.net.ssl.X509TrustManager
                )
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()

        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    @Provides
    @Singleton
    fun provideServiceApi(client: OkHttpClient): ServiceApi {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ServiceApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserRepository(impl: UserRepositoryImpl): UserRepository {
        return impl
    }
}