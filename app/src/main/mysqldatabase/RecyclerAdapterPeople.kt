package com.example.mysqldatabase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.mysqldatabase.Models.People
import kotlinx.android.synthetic.main.layout_list_people.view.*

class RecyclerAdapterPeople: Adapter<RecyclerView.ViewHolder>() {

    private var items: List<People> = ArrayList()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val id_number:TextView = itemView.people_id
        private val firstname: TextView = itemView.people_fname
        private val lastname: TextView = itemView.people_lname
        private val date: TextView = itemView.people_date

        fun bind(people_obj: People){
            id_number.setText(people_obj.id)
            firstname.setText(people_obj.fname)
            lastname.setText(people_obj.lname)
            date.setText(people_obj.date)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_list_people, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ViewHolder ->{
                holder.bind(items.get(position))
            }

        }
    }


    override fun getItemCount(): Int {
        return items.size;
    }


    fun submitList(peopleList: List<People>){
        items = peopleList
    }

}