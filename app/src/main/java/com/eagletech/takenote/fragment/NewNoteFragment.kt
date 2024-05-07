package com.eagletech.takenote.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.eagletech.takenote.R
import com.eagletech.takenote.activities.MainActivity
import com.eagletech.takenote.activities.PayActivity
import com.eagletech.takenote.databinding.FragmentNewNoteBinding
import com.eagletech.takenote.model.Note
import com.eagletech.takenote.sharepref.SharedPreferencesManager
import com.eagletech.takenote.viewmodel.NoteViewModel
import com.google.android.material.snackbar.Snackbar


class NewNoteFragment : Fragment(R.layout.fragment_new_note) {
    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    private var _binding: FragmentNewNoteBinding? = null


    private val binding get() = _binding!!

    private lateinit var mView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferencesManager = SharedPreferencesManager.getInstance(requireContext())
        setHasOptionsMenu(true)
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNewNoteBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_new_note, menu)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> {
                saveNote(requireView())
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun saveNote(view: View) {
        val noteTitle = binding.etNoteTitle.text.toString().trim()
        val noteBody = binding.etNoteBody.text.toString().trim()

        if (noteTitle.isNotEmpty()) {
            val note = Note( 0,noteTitle, noteBody)
            noteViewModel.addNote(note)
            Snackbar.make(
                view, "Note saved successfully",
                Snackbar.LENGTH_SHORT
            ).show()
            view.findNavController().navigate(R.id.action_newNoteFragment_to_homeFragment)
            if (sharedPreferencesManager.getLives() > 0) {
                sharedPreferencesManager.removeLife()

                // Continue playing
            } else {
                // Return to buy screen if no lives left
                val intent = Intent(requireContext(), PayActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                requireActivity().finish()
            }

        } else {
            Toast.makeText(
                requireContext(),
                "Please enter note title",
                Toast.LENGTH_LONG
            ).show()
        }
    }


    private val noteViewModel: NoteViewModel by lazy {
        val application = requireActivity().application
        ViewModelProvider(
            this,
            NoteViewModel.NoteViewModelFactory(application)
        )[NoteViewModel::class.java]
    }


}