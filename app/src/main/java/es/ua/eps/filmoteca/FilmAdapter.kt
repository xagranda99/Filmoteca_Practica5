package es.ua.eps.filmoteca

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class FilmAdapter(context: Context?, resource: Int, objects: List<Film>?) : ArrayAdapter<Film>(context!!, resource, objects!!) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view: View = convertView?: LayoutInflater.from(this.context)
            .inflate(R.layout.film_list_layout, parent, false)

        val tvDirector = view.findViewById(R.id.director) as TextView
        val tvTitle = view.findViewById(R.id.title) as TextView
        val ivImageResId = view.findViewById(R.id.imageResId) as ImageView

        getItem(position)?.let {
            tvDirector.text = it.director
            tvTitle.text = it.title
            ivImageResId.setImageResource(it.imageResId)
        }

        return view
    }
}
