package com.eagletech.takenote.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.eagletech.takenote.R
import com.eagletech.takenote.adapter.NoteAdapter
import com.eagletech.takenote.databinding.FragmentHomeBinding
import com.eagletech.takenote.model.Note
import com.eagletech.takenote.state.NoteDataState
import com.eagletech.takenote.viewmodel.NoteViewModel
import kotlinx.coroutines.launch


class HomeFragment : Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fabAddNote.setOnClickListener { mView ->
            mView.findNavController().navigate(R.id.action_homeFragment_to_newNoteFragment)
        }

        initControls()

    }

    private fun initControls() {
        val adapter: NoteAdapter = NoteAdapter(requireContext(), onClickItem)

        binding.recyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
            )
            setHasFixedSize(true)

        }
        binding.recyclerView.adapter = adapter


        noteViewModel.getAllNotes()


        lifecycleScope.launch {
            noteViewModel._noteDataStateFlow.collect { it ->
                when (it) {
                    is NoteDataState.Loading -> {
//                        Toast.makeText(
//                            requireContext(),
//                            "Loading data...",
//                            Toast.LENGTH_LONG
//                        ).show()


                    }

                    is NoteDataState.Failure -> {
//                        Toast.makeText(
//                            requireContext(),
//                            "Error Get Data Quiz ${it.msg}",
//                            Toast.LENGTH_LONG
//                        ).show()

                    }

                    is NoteDataState.Success -> {
//                        Toast.makeText(
//                            requireContext(),
//                            "Get Data Success!",
//                            Toast.LENGTH_LONG
//                        ).show()
                        adapter.setNotes(it.data)
                        binding.tvNoNoteAvailable.isVisible = false
                        binding.recyclerView.isVisible = true
                    }

                    is NoteDataState.Empty -> {
//                        Toast.makeText(
//                            requireContext(),
//                            "Data Is Empty",
//                            Toast.LENGTH_LONG
//                        ).show()
                        adapter.setNotes(it.data)
                        binding.tvNoNoteAvailable.isVisible = true
                        binding.recyclerView.isVisible = false
                    }
                }

            }
        }


    }


    private val onClickItem: (Note) -> Unit = {
        Log.d("click", "click")
        val bundle = bundleOf(
            "noteUpdate" to it
        )
        findNavController().navigate(R.id.action_homeFragment_to_updateNoteFragment, bundle)


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private val noteViewModel: NoteViewModel by lazy {
        val application = requireActivity().application
        ViewModelProvider(
            this,
            NoteViewModel.NoteViewModelFactory(application)
        )[NoteViewModel::class.java]
    }


}