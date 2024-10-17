package es.ua.eps.filmoteca

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.constraintlayout.compose.Visibility
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavHostController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.ua.eps.filmoteca.databinding.ActivityAboutBinding
import es.ua.eps.filmoteca.databinding.ActivityFilmListBinding

private val mode = Mode.Compose
private val LIST_MODE = false
private lateinit var bindingFilmListActivity: ActivityFilmListBinding

class FilmListActivity : AppCompatActivity() {
    var recyclerView: RecyclerView? = null
    var adapter: RecyclerView.Adapter<*>? = null
    var layoutManager: RecyclerView.LayoutManager? = null


    private lateinit var filmAdapter: FilmAdapter
    private var actionMode: ActionMode? = null

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_film_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_add_movie -> {
                // Acción para añadir una película
                addFilm()
                true
            }
            R.id.menu_about -> {
                // Acción para mostrar la actividad "Acerca de"
                navigate_about(this)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addFilm() {
        val newFilm = Film(getString(R.string.new_title), getString(R.string.new_director), R.drawable.regreso_al_futuro_ii)
        filmAdapter.add(newFilm)
        filmAdapter.notifyDataSetChanged()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film_list)

        // Inicializa tu adaptador
        val movieList = mutableListOf<Film>() // Llena la lista de películas
        filmAdapter = FilmAdapter(this, R.layout.film_list_layout, movieList)

        // Configura el ListView con el adaptador
        val listView = findViewById<ListView>(R.id.list_films)
        listView.adapter = filmAdapter

        listView.choiceMode = ListView.CHOICE_MODE_MULTIPLE

        listView.setOnItemClickListener { parent, view, position, id ->
            // Alterna la selección
            filmAdapter.toggleSelection(position)
            actionMode?.invalidate() // Actualiza el ActionMode
        }

        // Lógica para la pulsación larga y activar ActionMode
        listView.setOnItemLongClickListener { _, _, position, _ ->
            if (actionMode == null) {
                actionMode = startActionMode(actionModeCallback)
            }
            filmAdapter.toggleSelection(position)
            actionMode?.invalidate() // Esto fuerza la actualización del ActionMode para mostrar el número de seleccionados
            true
        }
    }

    private val actionModeCallback = object : ActionMode.Callback {
        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            menuInflater.inflate(R.menu.menu_action_mode, menu)
            return true
        }

        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            return when (item?.itemId) {
                R.id.menu_delete -> {
                    deleteSelectedFilms()
                    mode?.finish() // Termina el ActionMode
                    true
                }
                else -> false
            }
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            actionMode = null
            filmAdapter.clearSelection() // Limpia las selecciones cuando termina el ActionMode
        }

        // Aquí es donde actualizamos el título del ActionMode con el número de elementos seleccionados
        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            mode?.title = "${filmAdapter.getSelectedCount()} seleccionadas"
            return true
        }
    }

    private fun deleteSelectedFilms() {
        val selectedFilms = filmAdapter.getSelectedItems()
        selectedFilms.forEach { film ->
            filmAdapter.remove(film) // Remueve las películas seleccionadas del adaptador
        }
        filmAdapter.notifyDataSetChanged()
    }

}

private fun initUI(context: FilmListActivity) {
    when (mode) {
        Mode.Layouts -> initLayouts(context)
        Mode.Compose -> context.setContent { initCompose(context) }
    }
}

private fun initLayouts(context: FilmListActivity) {
    if (LIST_MODE) {
        init_ListView(context)
    } else {
        init_RecyclerView(context)
    }
}

@Composable
private fun initCompose(context: FilmListActivity) {
    MaterialTheme {
        Surface(
            color = Color.White,
            modifier = Modifier.fillMaxSize()

        ) {
            LazyColumn(modifier = Modifier.padding(top = 50.dp)) {
                items(FilmDataSource.films) { film ->
                    itemFilm(context, film)
                }
            }
        }
    }
}

@Composable
fun itemFilm(context: FilmListActivity, film: Film) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(3.dp)
        .clickable { navigate_data_film(context, FilmDataSource.films.indexOf(film)) }
    ) {
        Image(
            painter = painterResource(id = film.imageResId),
            contentDescription = "",
            modifier = Modifier.size(50.dp)
        )
        Column {
            Text(
                text = film.title.toString(),
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = film.director.toString(),
            )
        }
    }
}

private fun init_ListView(context: FilmListActivity) {
    bindingFilmListActivity = ActivityFilmListBinding.inflate(context.layoutInflater)
    bindingFilmListActivity.listFilms.visibility = View.VISIBLE
    //bindingFilmListActivity.listFilmsRecycler.visibility = View.GONE
    context.setContentView(bindingFilmListActivity.root)


    val listFilms = FilmDataSource.films

    val adapterListFilms = FilmAdapter(
        context,
        R.layout.film_list_layout,
        listFilms
    )
    bindingFilmListActivity.listFilms.adapter = adapterListFilms

    bindingFilmListActivity.listFilms.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
        navigate_data_film(context, position)
    })
}

private fun init_RecyclerView(context: FilmListActivity) {
    bindingFilmListActivity = ActivityFilmListBinding.inflate(context.layoutInflater)
    bindingFilmListActivity.listFilms.visibility = View.GONE
    //bindingFilmListActivity.listFilmsRecycler.visibility = View.VISIBLE
    context.setContentView(bindingFilmListActivity.root)

    //context.recyclerView = bindingFilmListActivity.listFilmsRecycler
    context.recyclerView?.layoutManager = LinearLayoutManager(context)
    val adapter = FilmRecyclerAdapter(FilmDataSource.films)
    adapter.setOnItemClickListener {
        film: Film ->  navigate_data_film(context, FilmDataSource.films.indexOf(film))
    }
    context.recyclerView?.adapter = adapter
}

private fun navigate_data_film(context: FilmListActivity, indexFilm: Int) {
    val intent_edit_film = Intent(context, FilmDataActivity::class.java)
    intent_edit_film.putExtra(FilmDataActivity.EXTRA_FILM_INDEX, indexFilm)
    intent_edit_film.putExtra("MODE", mode.toString())
    context.startActivity(intent_edit_film)
}

private fun navigate_about(context: FilmListActivity) {
    val intent_about = Intent(context, AboutActivity::class.java)
    intent_about.putExtra("MODE", mode.toString())
    context.startActivity(intent_about)
}