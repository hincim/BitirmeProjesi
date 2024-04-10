package com.example.muhendisliktasarimi.module

import com.example.muhendisliktasarimi.data.remote.dto.TdkAPI
import com.example.muhendisliktasarimi.data.repo.TdkRepoImpl
import com.example.muhendisliktasarimi.domain.repo.TdkRepo
import com.example.muhendisliktasarimi.util.Constants.TDK_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTdkApi() : TdkAPI {

        return Retrofit.Builder()
            .baseUrl(TDK_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TdkAPI::class.java)
    }

    @Singleton
    @Provides
    fun injectRepo(api: TdkAPI) = TdkRepoImpl(api) as TdkRepo

}