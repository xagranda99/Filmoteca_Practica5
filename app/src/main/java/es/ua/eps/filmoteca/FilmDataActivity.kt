package es.ua.eps.filmoteca

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.ContextCompat.getString
import androidx.core.content.ContextCompat.startActivity
import es.ua.eps.filmoteca.Film.Companion.FORMAT_BLURAY
import es.ua.eps.filmoteca.Film.Companion.FORMAT_DIGITAL
import es.ua.eps.filmoteca.Film.Companion.FORMAT_DVD
import es.ua.eps.filmoteca.Film.Companion.GENRE_ACTION
import es.ua.eps.filmoteca.Film.Companion.GENRE_COMEDY
import es.ua.eps.filmoteca.Film.Companion.GENRE_DRAMA
import es.ua.eps.filmoteca.Film.Companion.GENRE_HORROR
import es.ua.eps.filmoteca.Film.Companion.GENRE_SCIFI
import es.ua.eps.filmoteca.databinding.ActivityFilmDataBinding

private lateinit var mode: Mode
private lateinit var bindingFilmDataActivity: ActivityFilmDataBinding

class FilmDataActivity : AppCompatActivity() {
    companion object {
        val EXTRA_FILM_INDEX = "EXTRA_FILM_INDEX"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mode = Mode.valueOf(this.intent.getStringExtra("MODE").toString())
        initUI(this)
    }
}

private fun initUI(context: FilmDataActivity) {
    when (mode) {
        Mode.Layouts -> initLayouts(context)
        Mode.Compose -> context.setContent { initCompose(context) }
    }
}

private fun initLayouts(context: FilmDataActivity) {
    bindingFilmDataActivity = ActivityFilmDataBinding.inflate(context.layoutInflater)
    context.setContentView(bindingFilmDataActivity.root)

    loadFilmData(context)

    bindingFilmDataActivity.idEditFilm.setOnClickListener { navigate_edit_film(context) }
    bindingFilmDataActivity.idBackHome.setOnClickListener { navigate_list_film(context) }
}

private fun loadFilmData(context: FilmDataActivity) {
    val Film = FilmDataSource.films[context.intent.getIntExtra(FilmDataActivity.EXTRA_FILM_INDEX, 0)]
    var format = ""
    var genre = ""

    when (Film.format) {
        FORMAT_DVD -> { format = "DVD" }
        FORMAT_BLURAY -> { format = "BLU-RAY" }
        FORMAT_DIGITAL -> { format = "DIGITAL" }
    }

    when (Film.genre) {
        GENRE_ACTION -> { genre = "ACTION" }
        GENRE_COMEDY -> { genre = "COMEDY" }
        GENRE_DRAMA -> { genre = "DRAMA" }
        GENRE_SCIFI -> { genre = "SCI-FI" }
        GENRE_HORROR -> { genre = "HORROR" }
    }

    bindingFilmDataActivity.idTitleFilm.text = Film.title
    bindingFilmDataActivity.imageView.setImageDrawable(context.getDrawable(Film.imageResId))
    bindingFilmDataActivity.idNameDirector.text = Film.director
    bindingFilmDataActivity.idAnyo.text = Film.year.toString()
    bindingFilmDataActivity.idGenre.text = genre
    bindingFilmDataActivity.idFormat.text = format
    bindingFilmDataActivity.idComments.text = Film.comments
    bindingFilmDataActivity.idWatchFilm.setOnClickListener {
        val intent_web =
            Intent(Intent.ACTION_VIEW, Uri.parse(Film.imdbUrl))
        startActivity(context, intent_web, null)
        }
}

@Composable
private fun initCompose(context: FilmDataActivity) {
    MaterialTheme {
        Surface(
            color = Color.White,
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn {
                item {
                    ContraintLayoutData(context)
                }
            }
        }
    }
}

@Composable
private fun ContraintLayoutData(context: FilmDataActivity) {
    val film = FilmDataSource.films[context.intent.getIntExtra(FilmDataActivity.EXTRA_FILM_INDEX, 0)]
    var formatText: String = ""
    var genreText: String = ""

    when (film.format) {
        FORMAT_DVD -> { formatText = "DVD" }
        FORMAT_BLURAY -> { formatText = "BLU-RAY" }
        FORMAT_DIGITAL -> { formatText = "DIGITAL" }
    }

    when (film.genre) {
        GENRE_ACTION -> { genreText = "ACTION" }
        GENRE_COMEDY -> { genreText = "COMEDY" }
        GENRE_DRAMA -> { genreText = "DRAMA" }
        GENRE_SCIFI -> { genreText = "SCI-FI" }
        GENRE_HORROR -> { genreText = "HORROR" }
    }

    ConstraintLayout(Modifier.fillMaxSize()) {
        val (title, text_director, director, text_anyo, anyo,
            format, genre, button_watch_film, comments,
            button_back_home, button_edit_film, picture) = createRefs()

        Text(
            text = film.title.toString(),
            fontSize = 20.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.constrainAs(title) {
                top.linkTo(parent.top, margin = 100.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
        Text(
            text = getString(context, R.string.text_director),
            fontSize = 14.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.constrainAs(text_director) {
                top.linkTo(title.bottom, margin = 92.dp)
                start.linkTo(picture.end)
                end.linkTo(parent.end)
            }
        )
        Image(
            painter = painterResource(id = film.imageResId),
            contentDescription = "",
            Modifier
                .height(175.dp)
                .width(100.dp)
                .constrainAs(picture) {
                    top.linkTo(title.bottom, margin = 92.dp)
                    start.linkTo(parent.start, margin = 30.dp)
                }
        )
        Text(
            text = film.director.toString(),
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.constrainAs(director) {
                top.linkTo(text_director.bottom, margin = 12.dp)
                start.linkTo(picture.end)
                end.linkTo(parent.end)
            }
        )
        Text(
            text = getString(context, R.string.text_anyo),
            fontSize = 14.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.constrainAs(text_anyo) {
                top.linkTo(director.bottom, margin = 12.dp)
                start.linkTo(picture.end)
                end.linkTo(parent.end)
            }
        )
        Text(
            text = film.year.toString(),
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.constrainAs(anyo) {
                top.linkTo(text_anyo.bottom, margin = 12.dp)
                start.linkTo(picture.end)
                end.linkTo(parent.end)
            }
        )
        Text(
            text = formatText,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.constrainAs(format) {
                top.linkTo(anyo.bottom, margin = 12.dp)
                start.linkTo(picture.end, margin = 70.dp)
            }
        )
        Text(
            text = genreText,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.constrainAs(genre) {
                top.linkTo(anyo.bottom, margin = 12.dp)
                start.linkTo(format.end)
                end.linkTo(parent.end)
            }
        )
        Text(
            text = film.comments.toString(),
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.constrainAs(comments) {
                top.linkTo(picture.bottom, margin = 36.dp)
                start.linkTo(parent.start, margin = 30.dp)
            }
        )
        Button(
            modifier = Modifier.constrainAs(button_watch_film) {
                top.linkTo(picture.bottom, margin = 92.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            onClick = {
                startActivity(
                    context,
                    Intent(Intent.ACTION_VIEW, Uri.parse(film.imdbUrl)),
                    null
                )
            }) {
            Text(text = stringResource(R.string.text_watch_film))
        }
        Button(
            onClick = { navigate_list_film(context) },
            modifier = Modifier.constrainAs(button_back_home) {
                top.linkTo(button_watch_film.bottom, margin = 72.dp)
                start.linkTo(parent.start)
                end.linkTo(button_edit_film.end, margin = 130.dp)
            },
        ) {
            Text(text = stringResource(R.string.text_back_to_home))
        }
        Button(
            onClick = { navigate_edit_film(context) },
            modifier = Modifier.constrainAs(button_edit_film) {
                top.linkTo(button_watch_film.bottom, margin = 72.dp)
                start.linkTo(button_back_home.start, margin = 170.dp)
                end.linkTo(parent.end)
            },
        ) {
            Text(text = stringResource(R.string.text_edit_film))
        }
    }
}

private fun navigate_edit_film(context: FilmDataActivity) {
    val intent_edit_film = Intent(context, FilmEditActivity::class.java)
    intent_edit_film.putExtra(
        FilmDataActivity.EXTRA_FILM_INDEX,
        context.intent.getStringExtra(FilmDataActivity.EXTRA_FILM_INDEX)
    )
    intent_edit_film.putExtra("MODE", mode.toString())
    context.startActivity(intent_edit_film)
}

private fun navigate_list_film(context: FilmDataActivity) {
    val intent_list_film = Intent(context, FilmListActivity::class.java)
    intent_list_film.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
    context.startActivity(intent_list_film)
}
