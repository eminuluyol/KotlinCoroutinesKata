package com.taurus.kotlincoroutineskata

import android.os.Looper
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction

fun logd(message: String) = Log.d("Coroutine Recipes", message)

fun getThreadMessage() = " [Is main thread ${Looper.myLooper() == Looper.getMainLooper()}] "

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

inline fun FragmentActivity.fragmentTransaction(func: FragmentTransaction.() -> Unit) {
    val ft = supportFragmentManager.beginTransaction()
    ft.func()
    ft.commit()
}

