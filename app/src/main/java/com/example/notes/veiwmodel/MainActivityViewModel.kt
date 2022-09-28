package com.example.notes.veiwmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.notes.db.AppDB
import com.example.notes.db.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivityViewModel(application: Application):AndroidViewModel(application) {
    val mainModel = MutableLiveData<List<Note>>()
    val noteDao = AppDB.getInstance(application).notesDao()

    fun addNote(note: Note){
        GlobalScope.launch {
            noteDao.insertNote(note)
            val newData = noteDao.getAll()
            GlobalScope.launch(Dispatchers.Main) {
                mainModel.value = newData
            }
        }
    }

    fun updateNote(note: Note){
        GlobalScope.launch {
            noteDao.updateNote(note)
            val newData = noteDao.getAll()
            GlobalScope.launch(Dispatchers.Main) {
                mainModel.value = newData
            }
        }
    }

    fun deleteNote(note:Note){
        GlobalScope.launch {
            noteDao.delete(note)
            val newData = noteDao.getAll()
            GlobalScope.launch(Dispatchers.Main) {
                mainModel.value = newData
            }
        }
    }

    fun getAllNote(){
        GlobalScope.launch {
            val newData = noteDao.getAll()
            GlobalScope.launch(Dispatchers.Main) {
                mainModel.value = newData
            }
        }
    }



}