package ng.i.cast.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import ng.i.cast.R
import ng.i.cast.model.AuditionList

class FilmAdapter(private var projectList: List<AuditionList>?, private var context: Context) :
        RecyclerView.Adapter<FilmAdapter.CardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = inflater.inflate(R.layout.custom_film, parent, false)

        return CardViewHolder(v)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {


    }

    override fun getItemCount(): Int {
        return 2
    }

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textProjectName: TextView?= null
        val textRole: TextView
        val edit: ImageButton
        val textYear : TextView
        val textJobType : TextView
        val textDirector : TextView
        val textProduction: TextView



        init {
            textProjectName = itemView.findViewById(R.id.textFilmName)
            textRole = itemView.findViewById(R.id.textRole)
            edit = itemView.findViewById(R.id.buttonEdit)
            textYear = itemView.findViewById(R.id.textYear)
            textJobType = itemView.findViewById(R.id.textJobType)
            textDirector = itemView.findViewById(R.id.textDirector)
            textProduction = itemView.findViewById(R.id.textProduction)}
    }


}
