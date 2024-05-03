package com.eagletech.takenote.state

import com.eagletech.takenote.model.Note

sealed class NoteDataState {
    data object Loading : NoteDataState()
    class Failure(val msg: Throwable) : NoteDataState()
    class Success(val data: List<Note>) : NoteDataState()
    class Empty(val data: List<Note>) : NoteDataState()
}
