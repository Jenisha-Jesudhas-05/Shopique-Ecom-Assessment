package com.example.shopique.di

import android.app.Application
import androidx.room.Room
import com.example.shopique.data.local.CartDao
import com.example.shopique.data.local.FavoriteDao
import com.example.shopique.data.local.ProductDao
import com.example.shopique.data.local.ShopiqueDatabase
import com.example.shopique.data.remote.ApiService
import com.example.shopique.data.remote.repository.AuthRepositoryImpl
import com.example.shopique.data.remote.repository.CartRepositoryImpl
import com.example.shopique.data.remote.repository.ProductRepositoryImpl
import com.example.shopique.data.repository.FavoriteRepositoryImpl
import com.example.shopique.domain.repository.AuthRepository
import com.example.shopique.domain.repository.CartRepository
import com.example.shopique.domain.repository.FavoriteRepository
import com.example.shopique.domain.repository.ProductRepository
import coil.ImageLoader
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideImageLoader(
        application: Application,
        okHttpClient: OkHttpClient
    ): ImageLoader {
        return ImageLoader.Builder(application)
            .okHttpClient(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {

        val trustAllCerts = arrayOf<TrustManager>(
            object : X509TrustManager {
                override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
                override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
                override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            }
        )

        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, trustAllCerts, SecureRandom())

        return OkHttpClient.Builder()
            .sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)
            .hostnameVerifier { _, _ -> true }
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {

        return Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/")
            .client(okHttpClient)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(
        retrofit: Retrofit
    ): ApiService {

        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(
        application: Application
    ): ShopiqueDatabase {

        return Room.databaseBuilder(
            application,
            ShopiqueDatabase::class.java,
            "shopique_db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideProductDao(
        database: ShopiqueDatabase
    ): ProductDao {

        return database.productDao()
    }

    @Provides
    @Singleton
    fun provideCartDao(
        database: ShopiqueDatabase
    ): CartDao {

        return database.cartDao()
    }

    @Provides
    @Singleton
    fun provideFavoriteDao(
        database: ShopiqueDatabase
    ): FavoriteDao {

        return database.favoriteDao()
    }

    @Provides
    @Singleton
    fun provideProductRepository(
        apiService: ApiService,
        productDao: ProductDao
    ): ProductRepository {

        return ProductRepositoryImpl(
            apiService,
            productDao
        )
    }

    @Provides
    @Singleton
    fun provideCartRepository(
        cartDao: CartDao
    ): CartRepository {

        return CartRepositoryImpl(cartDao)
    }

    @Provides
    @Singleton
    fun provideFavoriteRepository(
        favoriteDao: FavoriteDao
    ): FavoriteRepository {

        return FavoriteRepositoryImpl(favoriteDao)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        apiService: ApiService,
        tokenManager: com.example.shopique.data.local.TokenManager
    ): AuthRepository {
        return AuthRepositoryImpl(apiService, tokenManager)
    }
}

