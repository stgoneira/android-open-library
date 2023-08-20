package cl.stgoneira.android.openlibraryws.ws

import retrofit2.http.GET
import retrofit2.http.Query

interface LibroService {

    @GET("/search.json")
    suspend fun buscar(@Query("q") terminoBusqueda:String): ResultadoBusquedaLibros

}