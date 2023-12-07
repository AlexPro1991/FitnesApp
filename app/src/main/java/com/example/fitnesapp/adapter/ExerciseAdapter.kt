package com.example.fitnesapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fitnesapp.R
import com.example.fitnesapp.databinding.DaisListItemBinding
import com.example.fitnesapp.databinding.ExercisrListItemBinding
import pl.droidsonroids.gif.GifDrawable

class ExerciseAdapter() :
    ListAdapter<ExerciseModel, ExerciseAdapter.ExerciseHolder>(MyComparator()) {
    class ExerciseHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ExercisrListItemBinding.bind(view)
        fun setData(exercise: ExerciseModel) = with(binding) {

            tvName1.text = exercise.name
            tvCont.text = exercise.time
            checkBox.isChecked = exercise.isDone
            imExercise.setImageDrawable(GifDrawable(root.context.assets, exercise.image))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.exercisr_list_item, parent, false)
        return ExerciseHolder(view)
    }

    override fun onBindViewHolder(holder: ExerciseHolder, position: Int) {
        holder.setData(getItem(position))
    }

    class MyComparator : DiffUtil.ItemCallback<ExerciseModel>() {
        override fun areItemsTheSame(oldItem: ExerciseModel, newItem: ExerciseModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ExerciseModel, newItem: ExerciseModel): Boolean {
            return oldItem == newItem
        }
    }

}