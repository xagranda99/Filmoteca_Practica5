package es.ua.eps.filmoteca

class Film {
    var imageResId = 0 // Propiedades de la clase
    var title: String? = null
    var director: String? = null
    var year = 0
    var genre = 0
    var format = 0
    var imdbUrl: String? = null
    var comments: String? = null

    override fun toString(): String {
        return title?:"<Sin titulo>" // Al convertir a cadena mostramos su título
    }

    constructor()

    constructor(director: String, title: String, imageResId : Int) {
        this.imageResId = imageResId
        this.title = title
        this.director = director
    }

    companion object {
        const val FORMAT_DVD = 0 // Formatos
        const val FORMAT_BLURAY = 1
        const val FORMAT_DIGITAL = 2
        const val GENRE_ACTION = 0 // Géneros
        const val GENRE_COMEDY = 1
        const val GENRE_DRAMA = 2
        const val GENRE_SCIFI = 3
        const val GENRE_HORROR = 4
    }
}