package com.example.fitnesapp.utils

import android.app.AlertDialog
import android.content.Context
import com.example.fitnesapp.R

object DialogManager {
    fun showDialog(context: Context, mId: Int, listener: Listener){
       val builder = AlertDialog.Builder(context)
        var dialog:AlertDialog? = null
       builder.setTitle(R.string.alert)
        builder.setMessage(mId)
        builder.setPositiveButton(R.string.reset){ _,_ ->
            listener.onClik()
            dialog?.dismiss()
        }
        dialog = builder.create()
        builder.setNegativeButton(R.string.back){ _,_ ->
            dialog?.dismiss()
        }
        dialog = builder.create()
        dialog.show()
    }
    interface Listener{
        fun onClik()
    }
}