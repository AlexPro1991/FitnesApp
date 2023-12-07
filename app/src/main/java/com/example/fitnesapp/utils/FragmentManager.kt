package com.example.fitnesapp.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.fitnesapp.R

object FragmentManager {
    var currentFragment: Fragment? = null
    fun setFragment(newFragment: Fragment, act: AppCompatActivity) {
        val transaction = act
            .supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            .replace(R.id.plaseHolder, newFragment)
        transaction.commit()
        currentFragment = newFragment
    }
}