package es.ua.eps.filmoteca

import android.content.Context
import android.graphics.Color
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class FilmAdapter(context: Context?, resource: Int, objects: List<Film>?) : ArrayAdapter<Film>(context!!, resource, objects!!) {
    private val selectedItems = SparseBooleanArray()

    // Devuelve cuántos elementos están seleccionados
    fun getSelectedCount(): Int {
        return selectedItems.size()
    }

    // Devuelve una lista de las películas seleccionadas
    fun getSelectedItems(): List<Film> {
        val selectedFilms = mutableListOf<Film>()
        for (i in 0 until selectedItems.size()) {
            if (selectedItems.valueAt(i)) {
                selectedFilms.add(getItem(selectedItems.keyAt(i))!!)
            }
        }
        return selectedFilms
    }

    // Alterna la selección de un elemento
    fun toggleSelection(position: Int) {
        if (selectedItems.get(position, false)) {
            selectedItems.delete(position)
        } else {
            selectedItems.put(position, true)
        }
        notifyDataSetChanged() // Refresca la lista para mostrar la selección
    }

    // Limpia todas las selecciones
    fun clearSelection() {
        selectedItems.clear()
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view: View = convertView ?: LayoutInflater.from(this.context)
            .inflate(R.layout.film_list_layout, parent, false)

        val tvDirector = view.findViewById(R.id.director) as TextView
        val tvTitle = view.findViewById(R.id.title) as TextView
        val ivImageResId = view.findViewById(R.id.imageResId) as ImageView

        getItem(position)?.let {
            tvDirector.text = it.director
            tvTitle.text = it.title
            ivImageResId.setImageResource(it.imageResId)
        }

        // Cambiar el color de fondo si el ítem está seleccionado
        if (selectedItems.get(position, false)) {
            view.setBackgroundColor(context.resources.getColor(R.color.selected_film)) // Puedes definir el color en res/values/colors.xml
        } else {
            view.setBackgroundColor(context.resources.getColor(android.R.color.transparent))
        }

        return view
    }
}