package ng.i.cast.adapter

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.squareup.picasso.Picasso
import ng.i.cast.R
import ng.i.cast.model.AuditionList

class AuditionAdapter(private var projectList: List<AuditionList>, private var context: Context) :
        RecyclerView.Adapter<AuditionAdapter.CardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = inflater.inflate(R.layout.custom_project, parent, false)

        return CardViewHolder(v)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {

        Picasso.with(context).load(projectList[position].image).into(holder.imageProject)
        holder.textProjectName!!.text = projectList[position].name
        holder.textDescription.text = projectList[position].description
        holder.textDescription.text = projectList[position].notificationCount

        if (projectList[position].notificationCount =="0"){
            holder.textCount.visibility = View.GONE
        }else {
            holder.textCount.text = projectList[position].notificationCount
            holder.textCount.visibility = View.VISIBLE
        }

        if (projectList[position].status =="active"){
            if (projectList[position].applied){
                holder.textApplied.visibility = View.VISIBLE
                holder.frameNotify.visibility = View.VISIBLE
                holder.buttonApply.visibility = View.GONE
            }
            else{
                holder.buttonApply.visibility = View.VISIBLE
                holder.frameNotify.visibility = View.GONE
                holder.textApplied.visibility = View.GONE

            }
        }else{
            if (projectList[position].applied){
                holder.textApplied.visibility = View.VISIBLE
                holder.frameNotify.visibility = View.VISIBLE
                holder.buttonApply.visibility = View.GONE
            }else {
                holder.buttonApply.visibility = View.VISIBLE
                holder.buttonApply.isEnabled = false
                holder.buttonApply.alpha =0.3f
                holder.frameNotify.visibility = View.GONE
                holder.textApplied.visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int {
        return projectList.size
    }

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textProjectName: TextView?= null
        val textDescription: TextView
        val buttonPreview: ImageButton
        val imageProject : ImageView
        val buttonNotify : ImageButton
        val textApplied : TextView
        val buttonApply: Button
        val frameNotify: FrameLayout
        val textCount : TextView


        init {
            textProjectName = itemView.findViewById(R.id.textName)
            textDescription = itemView.findViewById(R.id.textDescript)
            buttonPreview = itemView.findViewById(R.id.buttonPreview)
            imageProject = itemView.findViewById(R.id.imageProject)
            buttonNotify = itemView.findViewById(R.id.buttonNotification)
            textApplied = itemView.findViewById(R.id.textApplied)
            buttonApply = itemView.findViewById(R.id.buttonApply)
            frameNotify = itemView.findViewById(R.id.frameNotify)
            textCount = itemView.findViewById(R.id.textNotifyCount)
        }
    }


}
