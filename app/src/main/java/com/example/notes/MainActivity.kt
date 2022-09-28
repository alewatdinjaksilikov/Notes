package com.example.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.notes.db.Note
import com.example.notes.recyclerview.RecyclerAdapter
import com.example.notes.veiwmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainActivityViewModel
    lateinit var adapter: RecyclerAdapter
    var note: Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        adapter = RecyclerAdapter{
            btn_delete.visibility = View.VISIBLE
            note = it
            btn.setText("Edit")
            et_title.setText(it.title,TextView.BufferType.EDITABLE)
            et_desc.setText(it.description,TextView.BufferType.EDITABLE)
        }
        recyclerview.adapter = adapter
        subscribe()
        getAllNote()
        btn.setOnClickListener {
            if(btn.text == "Edit"){
                val newNote = note?.copy(title = et_title.text.toString(), description =et_desc.text.toString())
                updateNote(newNote!!)
                et_title.setText("",TextView.BufferType.EDITABLE)
                et_desc.setText("",TextView.BufferType.EDITABLE)
            }
            else{
                addNote(et_title.text.toString(),et_desc.text.toString())
                et_title.setText("",TextView.BufferType.EDITABLE)
                et_desc.setText("",TextView.BufferType.EDITABLE)
            }

        }

        btn_clear.setOnClickListener {
            btn_delete.visibility = View.GONE
            btn.setText("Add")
        }

        btn_delete.setOnClickListener {
            et_title.setText("",TextView.BufferType.EDITABLE)
            et_desc.setText("",TextView.BufferType.EDITABLE)
            deleteNote(note!!)
            btn_delete.visibility = View.GONE
            btn.setText("Add")
        }
    }

    fun subscribe(){
        viewModel.mainModel.observe(this, Observer {
            adapter.setList(it)
        })
    }

    fun addNote(title:String,desc:String){
        val note = com.example.notes.db.Note(System.currentTimeMillis(), title, desc)
        viewModel.addNote(note)
    }
    fun deleteNote(note: Note){
        viewModel.deleteNote(note)
    }
    fun updateNote(note: Note){
        viewModel.updateNote(note)
    }
    fun getAllNote(){
        viewModel.getAllNote()
    }
}