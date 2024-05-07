package com.eagletech.takenote.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.eagletech.takenote.db.repository.NoteRepository
import com.eagletech.takenote.model.Note
import com.eagletech.takenote.state.NoteDataState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : ViewModel() {
    private val noteRepository: NoteRepository = NoteRepository(application)


    private val noteDataStateFlow: MutableStateFlow<NoteDataState> = MutableStateFlow(
        NoteDataState.Empty(
            emptyList()
        )
    )

    val _noteDataStateFlow: StateFlow<NoteDataState> = noteDataStateFlow

    fun addNote(note: Note) = viewModelScope.launch { noteRepository.addNote(note) }
    fun updateNote(note: Note) = viewModelScope.launch { noteRepository.updateNote(note) }
    fun deleteNote(note: Note) = viewModelScope.launch { noteRepository.deleteNote(note) }

    fun getAllNotes() = viewModelScope.launch {
        noteDataStateFlow.value = NoteDataState.Loading
        noteRepository.getAllNote()
            .catch { e ->
                noteDataStateFlow.value = NoteDataState.Failure(e)
            }.collect { data ->
                if (data.isEmpty()) {
                    noteDataStateFlow.value = NoteDataState.Empty(data)
                } else {
                    noteDataStateFlow.value = NoteDataState.Success(data)
                }
            }
    }

    class NoteViewModelFactory(private val application: Application) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NoteViewModel(application) as T
            }
            throw IllegalArgumentException("Unable construct viewModel")
        }

    }
}