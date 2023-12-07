package com.example.fitnesapp.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitnesapp.R
import com.example.fitnesapp.adapter.DayModel
import com.example.fitnesapp.adapter.DaysAdapter
import com.example.fitnesapp.adapter.ExerciseAdapter
import com.example.fitnesapp.databinding.ExercisesListFragBinding
import com.example.fitnesapp.databinding.FragmentDaysBinding
import com.example.fitnesapp.utils.FragmentManager
import com.example.fitnesapp.utils.MainViewModel

class ExerciseListFragment : Fragment() {
    private lateinit var binding: ExercisesListFragBinding
    private lateinit var adapter: ExerciseAdapter
    private val model: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ExercisesListFragBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        model.mutableListExercise.observe(viewLifecycleOwner) {
            for (i in 0 until model.getExerciseCount()) {
                it[i] = it[i].copy(isDone = true)
            }
            adapter.submitList(it)
            binding.btPrew.setOnClickListener {
                FragmentManager.setFragment(
                    DaysFragment.newInstance(), activity as AppCompatActivity
                )
            }
        }

    }

    private fun init() = with(binding) {
        adapter = ExerciseAdapter()
        rcView.layoutManager = LinearLayoutManager(activity)
        rcView.adapter = adapter
        bStart.setOnClickListener {
            FragmentManager.setFragment(
                WaitingFragment.newInstance(), activity as AppCompatActivity
            )
        }
    }


    companion object {

        @JvmStatic
        fun newInstance() = ExerciseListFragment()
    }
}