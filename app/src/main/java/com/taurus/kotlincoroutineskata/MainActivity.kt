package com.taurus.kotlincoroutineskata

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.taurus.kotlincoroutineskata.R.id.fragmentContainer

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(fragmentContainer, SampleListFragment(), SampleListFragment.TAG)
                    .commitNow()
        }
    }

    override fun onBackPressed() {
        if (!supportFragmentManager.popBackStackImmediate())
            super.onBackPressed()
    }
}
