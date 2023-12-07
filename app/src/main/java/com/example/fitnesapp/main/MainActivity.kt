package com.example.fitnesapp.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.fitnesapp.Fragments.DaysFragment
import com.example.fitnesapp.R
import com.example.fitnesapp.utils.FragmentManager
import com.example.fitnesapp.utils.MainViewModel

class MainActivity : AppCompatActivity() {
    private val model: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        model.pref = getSharedPreferences("main", MODE_PRIVATE)
        FragmentManager.setFragment(DaysFragment.newInstance(), this)
    }

    override fun onBackPressed() {
        if(FragmentManager.currentFragment is DaysFragment)
        super.onBackPressed()
        else FragmentManager.setFragment(DaysFragment.newInstance(),this)
    }
}