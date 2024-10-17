package es.ua.eps.filmoteca

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FilmRecyclerAdapter(val films : List<Film>) : RecyclerView.Adapter<FilmRecyclerAdapter.ViewHolder?>() {

    private var listener: (film: Film) -> Unit = {}

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var title: TextView
        var director: TextView
        var imageRes: ImageView

        fun bind(f: Film) {
            title.text = f.title
            director.text = f.director
            imageRes.setImageResource(f.imageResId)
        }

        init {
            title = v.findViewById(R.id.title)
            director = v.findViewById(R.id.director)
            imageRes = v.findViewById(R.id.imageResId)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.film_list_layout, parent, false)
        val holder =  ViewHolder(v)

        v.setOnClickListener{
            val position: Int = holder.adapterPosition
            listener(films[position])
        }
        return holder
    }

    override fun getItemCount(): Int {
        return films.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(films[position])
    }

    fun setOnItemClickListener(listener: (film: Film) -> Unit) {
        this.listener = listener
    }
}