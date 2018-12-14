package ng.i.cast.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import ng.i.cast.R
import ng.i.cast.model.AuditionList

class EducationAdapter(private var projectList: List<AuditionList>?, private var context: Context) :
        RecyclerView.Adapter<EducationAdapter.CardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = inflater.inflate(R.layout.custom_education, parent, false)

        return CardViewHolder(v)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {


    }

    override fun getItemCount(): Int {
        return 2
    }

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textSchool: TextView?= null
        val textDegree: TextView
        val edit: ImageButton
        val textYear : TextView


        init {
            textSchool = itemView.findViewById(R.id.textSchool)
            textDegree = itemView.findViewById(R.id.textDegree)
            edit = itemView.findViewById(R.id.buttonEdit)
            textYear = itemView.findViewById(R.id.textYear)
        }
    }


}
