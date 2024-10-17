package es.ua.eps.filmoteca

object FilmDataSource {
    val films: MutableList<Film> = mutableListOf<Film>()

    init {
        var f1 = Film()
        f1.title = "Regreso al futuro"
        f1.director = "Robert Zemeckis"
        f1.imageResId = R.drawable.regreso_al_futuro_i
        f1.comments = ""
        f1.format = Film.Companion.FORMAT_DIGITAL
        f1.genre = Film.Companion.GENRE_SCIFI
        f1.imdbUrl = "http://www.imdb.com/title/tt0088763"
        f1.year = 1985
        films.add(f1)

        var f2 = Film()
        f2.title = "Regreso al futuro II"
        f2.director = "Robert Zemeckis"
        f2.imageResId = R.drawable.regreso_al_futuro_ii
        f2.comments = ""
        f2.format = Film.Companion.FORMAT_DIGITAL
        f2.genre = Film.Companion.GENRE_SCIFI
        f2.imdbUrl = "https://www.imdb.com/title/tt0096874/"
        f2.year = 1989
        films.add(f2)

        var f3 = Film()
        f3.title = "Shrek"
        f3.director = "Andrew Adamson"
        f3.imageResId = R.drawable.shrek
        f3.comments = ""
        f3.format = Film.Companion.FORMAT_DIGITAL
        f3.genre = Film.Companion.GENRE_COMEDY
        f3.imdbUrl = "https://www.imdb.com/title/tt0126029/"
        f3.year = 2001
        films.add(f3)

        var f4 = Film()
        f4.title = "Shrek II"
        f4.director = "Andrew Adamson"
        f4.imageResId = R.drawable.shrek_ii
        f4.comments = ""
        f4.format = Film.Companion.FORMAT_DIGITAL
        f4.genre = Film.Companion.GENRE_COMEDY
        f4.imdbUrl = "https://www.imdb.com/title/tt0298148/"
        f4.year = 2004
        films.add(f4)

        var f5 = Film()
        f5.title = "El señor de los anillos: La comunidad del anillo"
        f5.director = "Peter Jackson"
        f5.imageResId = R.drawable.el_senor_de_los_anillos_la_comunida_del_anillo
        f5.comments = ""
        f5.format = Film.Companion.FORMAT_DIGITAL
        f5.genre = Film.Companion.GENRE_SCIFI
        f5.imdbUrl = "https://www.imdb.com/title/tt0120737/"
        f5.year = 2001
        films.add(f5)

        var f6 = Film()
        f6.title = "El señor de los anillos: Las dos torres"
        f6.director = "Peter Jackson"
        f6.imageResId = R.drawable.el_senor_de_los_anillos_las_dos_torres
        f6.comments = ""
        f6.format = Film.Companion.FORMAT_DIGITAL
        f6.genre = Film.Companion.GENRE_SCIFI
        f6.imdbUrl = "https://www.imdb.com/title/tt0167261/"
        f6.year = 2002
        films.add(f6)

        var f7 = Film()
        f7.title = "El señor de los anillos: El retorno del rey"
        f7.director = "Peter Jackson"
        f7.imageResId = R.drawable.el_senor_de_los_anillos_el_retorno_del_rey
        f7.comments = ""
        f7.format = Film.Companion.FORMAT_DIGITAL
        f7.genre = Film.Companion.GENRE_SCIFI
        f7.imdbUrl = "https://www.imdb.com/title/tt0167260/"
        f7.year = 2003
        films.add(f7)
    }
}