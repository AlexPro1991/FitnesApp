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
import com.example.fitnesapp.adapter.ExerciseModel
import com.example.fitnesapp.databinding.FragmentDaysBinding
import com.example.fitnesapp.utils.DialogManager
import com.example.fitnesapp.utils.FragmentManager
import com.example.fitnesapp.utils.MainViewModel

class DaysFragment : Fragment(), DaysAdapter.Listener {
    private lateinit var binding: FragmentDaysBinding
    private lateinit var adapter: DaysAdapter
    private val model: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDaysBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        model.currentDay = 0
        init()
        btDelAll.setOnClickListener {
            DialogManager.showDialog(activity as AppCompatActivity,
                R.string.reset_days, object : DialogManager.Listener {
                    override fun onClik() {
                        model.pref?.edit()?.clear()?.apply()
                        adapter.submitList(fillDaysArray())
                    }
                })

        }
    }


    private fun init() = with(binding) {
        adapter = DaysAdapter(this@DaysFragment)
        rcDays.layoutManager = LinearLayoutManager(activity as AppCompatActivity)
        rcDays.adapter = adapter
        adapter.submitList(fillDaysArray())
    }

    private fun fillDaysArray(): ArrayList<DayModel> {
        val tArray = ArrayList<DayModel>()
        var daysDoneCounter = 0
        resources.getStringArray(R.array.day).forEach {
            model.currentDay++
            val exCounter = it.split(",").size
            tArray.add(DayModel(it, 0, model.getExerciseCount() == exCounter))
        }
        binding.progressBar.max = tArray.size
        tArray.forEach {
            if (it.isDone) daysDoneCounter++
        }
        updateRestDay(tArray.size - daysDoneCounter, tArray.size)
        return tArray
    }

    private fun updateRestDay(restDays: Int, days: Int) = with(binding) {
        val rDays = getString(R.string.rest) + " $restDays " + getString(R.string.rest_days)
        tvRestDays.text = rDays
        progressBar.progress = days - restDays
    }

    private fun fillExerciseList(day: DayModel) {
        val tempList = ArrayList<ExerciseModel>()
        day.exercises.split(",").forEach {
            val exerciseList = resources.getStringArray(R.array.exercise)
            val exercise = exerciseList[it.toInt()]
            val exerciseArray = exercise.split("|")
            tempList.add(
                ExerciseModel(
                    exerciseArray[0],
                    exerciseArray[1], false, exerciseArray[2]
                )
            )
        }
        model.mutableListExercise.value = tempList
    }

    companion object {

        @JvmStatic
        fun newInstance() = DaysFragment()
    }

    override fun onClick(day: DayModel) {
        if (!day.isDone) {

            fillExerciseList(day)
            model.currentDay = day.dayNumber
            FragmentManager.setFragment(
                ExerciseListFragment.newInstance(),
                activity as AppCompatActivity
            )
        } else {
            DialogManager.showDialog(activity as AppCompatActivity,
                R.string.reset_exercise, object : DialogManager.Listener {
                    override fun onClik() {
                        model.savePref(day.dayNumber.toString(), 0)
                        fillExerciseList(day)
                        model.currentDay = day.dayNumber
                        FragmentManager.setFragment(
                            ExerciseListFragment.newInstance(),
                            activity as AppCompatActivity
                        )
                    }
                }
            )
        }
    }
}