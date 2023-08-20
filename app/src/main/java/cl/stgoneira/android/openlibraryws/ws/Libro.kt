package cl.stgoneira.android.openlibraryws.ws

import com.squareup.moshi.Json

data class ResultadoBusquedaLibros(
    @Json(name = "numFound")
    val encontrados:Int,
    @Json(name = "docs")
    val libros: List<Libro>
)

data class Libro(
    @Json(name = "title")
    val titulo: String,
    @Json(name = "author_name")
    val autores: List<String> = emptyList<String>(),
    @Json(name = "cover_i")
    val imagenId: Long? = null
) {
    val imagenUrl:String
        get() {
            if(imagenId != null) {
                return "https://covers.openlibrary.org/b/id/${imagenId}-M.jpg"
            } else {
                return "https://upload.wikimedia.org/wikipedia/commons/1/14/No_Image_Available.jpg"
            }
        }
}
