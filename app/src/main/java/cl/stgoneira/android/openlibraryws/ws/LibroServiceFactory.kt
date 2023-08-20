package cl.stgoneira.android.openlibraryws.ws

import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object LibroServiceFactory {

    fun getBaseUrl():String {
        return "https://openlibrary.org"
    }

    fun getLibroService():LibroService {
        return getService(LibroService::class.java) as LibroService
    }

    fun getService( serviceClass:Class<*> ):Any {
        val adapter     = KotlinJsonAdapterFactory()
        val moshi       = Moshi.Builder().add(adapter).build()
        val converter   = MoshiConverterFactory.create(moshi)
        val retrofit    = Retrofit.Builder()
            .addConverterFactory(converter)
            .baseUrl( getBaseUrl() )
            .build()
        return retrofit.create( serviceClass )
    }

}