package com.example.fitnesapp.Fragments

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.fragment.app.activityViewModels
import com.example.fitnesapp.R
import com.example.fitnesapp.adapter.ExerciseModel
import com.example.fitnesapp.databinding.ExerciseBinding
import com.example.fitnesapp.utils.FragmentManager
import com.example.fitnesapp.utils.MainViewModel
import com.example.fitnesapp.utils.TimeUtils
import pl.droidsonroids.gif.GifDrawable

class ExerciseFragment : Fragment() {
    private lateinit var binding: ExerciseBinding
    private var exerciseCounter = 0
    private var exList: ArrayList<ExerciseModel>? = null
    private var timer: CountDownTimer? = null
    private var currentDay = 0
    private val model: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ExerciseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        currentDay = model.currentDay
        exerciseCounter = model.getExerciseCount()
        model.mutableListExercise.observe(viewLifecycleOwner) {
            exList = it
            nextExercise()
        }
        bNext.setOnClickListener {
               nextExercise()
        }
        btPrew.setOnClickListener {
            timer?.cancel()
            Toast.makeText(activity, "Вы не закончили тренеровку!", Toast.LENGTH_SHORT).show()
            FragmentManager.setFragment(
                DaysFragment.newInstance(), activity as AppCompatActivity
            )
        }
        btFinish.setOnClickListener {
            FragmentManager.setFragment(
                DaysFragment.newInstance(), activity as AppCompatActivity
            )
        }
    }

    private fun nextExercise() = with(binding) {
        if (exerciseCounter < exList?.size!!) {
            val ex = exList?.get(exerciseCounter++) ?: return
            showExercise(ex)
            setExerciseType(ex)
        } else {
            exerciseCounter++
            timer?.cancel()
            imMain.setImageResource(R.drawable.win)
            tvName.text = "ФИНИШ!"
            tvTime.text = ""
            btPrew.isInvisible = true
            bNext.isInvisible = true
            btFinish.isInvisible = false
        }
        model.savePref(currentDay.toString(),exerciseCounter - 1)
     }

    private fun showExercise(exercise: ExerciseModel) = with(binding) {
        imMain.setImageDrawable(GifDrawable(root.context.assets, exercise.image))
        tvName.text = exercise.name
        val title = "$exerciseCounter / ${exList?.size}"
        tvCountEx.text = title
    }

    private fun setExerciseType(exercise: ExerciseModel) {
        if (exercise.time.startsWith("x")) {
            timer?.cancel()
            binding.tvTime.text = exercise.time
        } else {
            startTimer(exercise)
        }
    }

    private fun startTimer(exercise: ExerciseModel) = with(binding) {
        timer?.cancel()
        timer = object : CountDownTimer(exercise.time.toLong() * 1000, 1) {
            override fun onTick(restTime: Long) {
                tvTime.text = TimeUtils.getTime(restTime)
            }

            override fun onFinish() {
                nextExercise()
            }
        }.start()
    }

    override fun onDetach() {
        super.onDetach()
        timer?.cancel()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ExerciseFragment()
    }
}