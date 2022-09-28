package com.example.notes.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.db.Note
import kotlinx.android.synthetic.main.list_item.view.*

class RecyclerAdapter(var onClick:(note:Note)->Unit):RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {

    val list = mutableListOf<Note>()

    fun setList(newList: List<Note>){
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    inner class MyViewHolder(view : View):RecyclerView.ViewHolder(view){
        fun setData(note: Note){
            itemView.tv_title.text = note.title
            itemView.tv_desc.text = note.description
            itemView.setOnClickListener {
                onClick(note)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.setData(list[position])
    }

    override fun getItemCount(): Int = list.size
}