package com.example.muhendisliktasarimi.module

import com.example.muhendisliktasarimi.data.remote.dto.MovieAPI
import com.example.muhendisliktasarimi.data.remote.dto.TdkAPI
import com.example.muhendisliktasarimi.data.remote.dto.WeatherAPI
import com.example.muhendisliktasarimi.data.repo.MovieRepoImpl
import com.example.muhendisliktasarimi.data.repo.TdkRepoImpl
import com.example.muhendisliktasarimi.data.repo.WeatherRepoImpl
import com.example.muhendisliktasarimi.domain.repo.MovieRepo
import com.example.muhendisliktasarimi.domain.repo.TdkRepo
import com.example.muhendisliktasarimi.domain.repo.WeatherRepo
import com.example.muhendisliktasarimi.util.Constants.MOVIE_BASE_URL
import com.example.muhendisliktasarimi.util.Constants.TDK_BASE_URL
import com.example.muhendisliktasarimi.util.Constants.WEATHER_BASE_URL
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
    fun provideWeatherApi() : WeatherAPI {

        return Retrofit.Builder()
            .baseUrl(WEATHER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherAPI::class.java)
    }
    @Provides
    @Singleton
    fun provideTdkApi() : TdkAPI {

        return Retrofit.Builder()
            .baseUrl(TDK_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TdkAPI::class.java)
    }
    @Provides
    @Singleton
    fun provideMovieApi() : MovieAPI {

        return Retrofit.Builder()
            .baseUrl(MOVIE_BASE_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieAPI::class.java)
    }

//    @Provides
//    @Singleton
//    fun provideWordApi() : WordsAPI {
//
//        return Retrofit.Builder()
//            .baseUrl(WORD_BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(WordsAPI::class.java)
//    }

    @Singleton
    @Provides
    fun injectRepo(api: TdkAPI) = TdkRepoImpl(api) as TdkRepo


    @Provides
    @Singleton
    fun provideWeatherRepository(api: WeatherAPI): WeatherRepo {

        return WeatherRepoImpl(api)
    }
    @Provides
    @Singleton
    fun provideMovieRepository(api: MovieAPI): MovieRepo {

        return MovieRepoImpl(api)
    }

//    @Provides
//    @Singleton
//    fun provideWordsRepository(api: WordsAPI): WordsRepo {
//
//        return WordsRepoImpl(api)
//    }

}