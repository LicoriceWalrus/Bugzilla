package com.bugzilla

import android.app.Application
import com.bugzilla.features.bugs.bugsModule
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            koin.loadModules(
                listOf(
                    network, bugsModule
                )
            )
        }

    }

    private val network = module {
        single<Gson> {
            GsonBuilder()
                .setLenient()
                .create()
        }
        single<Retrofit> {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(get()))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
        }
    }

    companion object {
        private const val BASE_URL = "https://bugzilla.mozilla.org/rest/"
    }
}