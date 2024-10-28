package ru.urfu.consecutivepractice.di

import com.google.gson.Gson
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.urfu.consecutivepractice.data.api.FormulaOneApi

val networkModule = module {
    factory { provideRetrofit() }
    single { provideNetworkApi(get()) }
}

fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://api.bthree.uk/f1/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient.Builder().build())
        .build()
}

fun provideNetworkApi(retrofit: Retrofit): FormulaOneApi =
    retrofit.create(FormulaOneApi::class.java)