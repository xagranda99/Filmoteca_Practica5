package es.ua.eps.filmoteca

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.ua.eps.filmoteca.Film.Companion.FORMAT_BLURAY
import es.ua.eps.filmoteca.Film.Companion.FORMAT_DIGITAL
import es.ua.eps.filmoteca.Film.Companion.FORMAT_DVD
import es.ua.eps.filmoteca.Film.Companion.GENRE_ACTION
import es.ua.eps.filmoteca.Film.Companion.GENRE_COMEDY
import es.ua.eps.filmoteca.Film.Companion.GENRE_DRAMA
import es.ua.eps.filmoteca.Film.Companion.GENRE_HORROR
import es.ua.eps.filmoteca.Film.Companion.GENRE_SCIFI
import es.ua.eps.filmoteca.databinding.ActivityFilmEditBinding

private lateinit var mode : Mode
private lateinit var bindingFilmEditActivity: ActivityFilmEditBinding

class FilmEditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mode = Mode.valueOf(this.intent.getStringExtra("MODE").toString())
        initUI(this)
    }
}

private fun initUI(context: FilmEditActivity) {
    when (mode) {
        Mode.Layouts -> initLayouts(context)
        Mode.Compose -> context.setContent { initCompose(context) }
    }
}

private fun initLayouts(context: FilmEditActivity) {
    bindingFilmEditActivity = ActivityFilmEditBinding.inflate(context.layoutInflater)
    context.setContentView(bindingFilmEditActivity.root)

    val Film = FilmDataSource.films[context.intent.getIntExtra(FilmDataActivity.EXTRA_FILM_INDEX, 0)]

    bindingFilmEditActivity.idTitleFilm.text= Editable.Factory.getInstance().newEditable(Film.title)
    bindingFilmEditActivity.imageView.setImageDrawable(context.getDrawable(Film.imageResId))
    bindingFilmEditActivity.idNameDirector.text = Editable.Factory.getInstance().newEditable(Film.director)
    bindingFilmEditActivity.idAnyo.text = Editable.Factory.getInstance().newEditable(Film.year.toString())
    bindingFilmEditActivity.idGenre.id = Film.genre
    bindingFilmEditActivity.idFormat.id = Film.format
    bindingFilmEditActivity.idComments.text = Editable.Factory.getInstance().newEditable(Film.comments)

    bindingFilmEditActivity.idTakePhoto.setOnClickListener{ action_take_photo(context) }
    bindingFilmEditActivity.idSelectPhoto.setOnClickListener{ action_select_photo(context) }
    bindingFilmEditActivity.idSave.setOnClickListener { action_save(context) }
    bindingFilmEditActivity.idCancel.setOnClickListener { action_cancel(context) }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun initCompose(context: FilmEditActivity) {
    val film = FilmDataSource.films[context.intent.getIntExtra(FilmDataActivity.EXTRA_FILM_INDEX, 0)]
    var format = ""
    var genre = ""

    when (film.format) {
        FORMAT_DVD -> { format = "DVD" }
        FORMAT_BLURAY -> { format = "BLU-RAY" }
        FORMAT_DIGITAL -> { format = "DIGITAL" }
    }

    when (film.genre) {
        GENRE_ACTION -> { genre = "ACTION" }
        GENRE_COMEDY -> { genre = "COMEDY" }
        GENRE_DRAMA -> { genre = "DRAMA" }
        GENRE_SCIFI -> { genre = "SCI-FI" }
        GENRE_HORROR -> { genre = "HORROR" }
    }

    var textAnyo by remember { mutableStateOf("") }
    val listGenre = listOf(stringResource(R.string.text_action), stringResource(R.string.text_drama),
        stringResource(R.string.text_comedy), stringResource(R.string.text_terror), "Sci-Fi")
    var expandedGender by remember { mutableStateOf(false)}
    var currentGender by remember { mutableStateOf((genre))}
    val listFormat = listOf("DVD", "Blu-ray", "Online")
    var expandedFormat by remember { mutableStateOf(false)}
    var currentFormat by remember { mutableStateOf((format))}

    MaterialTheme {
        Surface(
            color = Color.White,
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn {
                item {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 100.dp)
                    ) {
                        Image(
                            painter = painterResource(id = film.imageResId),
                            contentDescription = "Logo",
                            Modifier
                                .height(50.dp)
                                .width(50.dp)
                                .padding(horizontal = 5.dp)
                        )
                        Button(onClick = { action_take_photo(context) }, Modifier.padding(horizontal = 5.dp)) {
                            Text(text = stringResource(R.string.text_take_photo))
                        }
                        Button(onClick = { action_select_photo(context) }, Modifier.padding(horizontal = 5.dp)) {
                            Text(text = stringResource(R.string.text_select_image))
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                    ) {
                        TextField(
                            value = film.title.toString(),
                            onValueChange = { film.title = it },
                            label = { Text(stringResource(R.string.text_title_film)) },
                            modifier = Modifier.fillMaxWidth()

                       )
                    }
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                    ) {
                        TextField(
                            value = film.director.toString(),
                            onValueChange = { film.director = it },
                            label = { Text(stringResource(R.string.text_name_director)) },
                            modifier = Modifier.fillMaxWidth()

                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                    ) {
                        TextField(
                            value = film.year.toString(),
                            onValueChange = { textAnyo = it },
                            label = { Text(stringResource(R.string.text_anyo)) },
                            modifier = Modifier.fillMaxWidth()

                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp)
                    ) {
                        TextField(
                            value = film.imdbUrl.toString(),
                            onValueChange = { film.imdbUrl = it },
                            label = { Text(stringResource(R.string.text_url_imdb)) },
                            modifier = Modifier.fillMaxWidth()

                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                    ) {
                        ExposedDropdownMenuBox(
                            expanded = expandedGender,
                            onExpandedChange = { expandedGender = !expandedGender }
                        ) {
                            TextField(
                                modifier = Modifier.fillMaxWidth().menuAnchor(),
                                value = currentGender,
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon( expanded = expandedGender ) }
                            )

                            ExposedDropdownMenu(expanded = expandedGender,
                                onDismissRequest = { expandedGender = false }
                            ) {
                                listGenre.forEachIndexed{
                                    index, s ->
                                    DropdownMenuItem(
                                        text = { Text(text = s) },
                                        onClick = {
                                            currentGender = listGenre[index]
                                            expandedGender = false
                                        },
                                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                                    )
                                }
                            }
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                    ) {
                        ExposedDropdownMenuBox(
                            expanded = expandedFormat,
                            onExpandedChange = { expandedFormat = !expandedFormat }
                        ) {
                            TextField(
                                modifier = Modifier.fillMaxWidth().menuAnchor(),
                                value = currentFormat,
                                onValueChange = {},
                                readOnly = true,
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon( expanded = expandedFormat ) }
                            )

                            ExposedDropdownMenu(expanded = expandedFormat,
                                onDismissRequest = { expandedFormat = false }
                            ) {
                                listFormat.forEachIndexed{
                                        index, s ->
                                    DropdownMenuItem(
                                        text = { Text(text = s) },
                                        onClick = {
                                            currentFormat = listFormat[index]
                                            expandedFormat = false
                                        },
                                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                                    )
                                }
                            }
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                    ) {
                        TextField(
                            value = film.comments.toString(),
                            onValueChange = { film.comments = it },
                            label = { Text(stringResource(R.string.text_comments)) },
                            modifier = Modifier.fillMaxWidth()

                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 100.dp)
                    ) {
                        Button(onClick = { action_cancel(context) }, Modifier.padding(horizontal = 5.dp)) {
                            Text(text = stringResource(R.string.text_cancel))
                        }
                        Button(onClick = { action_save(context) }, Modifier.padding(horizontal = 5.dp)) {
                            Text(text = stringResource(R.string.text_save))
                        }
                    }
                }
            }
        }
    }
}

private fun action_save(context: FilmEditActivity) {
    context.setResult(Activity.RESULT_OK)
    context.finish()
}

private fun action_cancel(context: FilmEditActivity) {
    context.setResult(Activity.RESULT_CANCELED)
    context.finish()
}

private fun action_take_photo(context: FilmEditActivity) {
    try {
        context.startActivity(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(context, "No se ha podido abrir la camara", Toast.LENGTH_SHORT).show()
    }
}

private fun action_select_photo(context: FilmEditActivity) {
    try {
        context.startActivity(Intent(MediaStore.ACTION_PICK_IMAGES))
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(context, "No se ha podido abrir la galeria", Toast.LENGTH_SHORT).show()
    }
}