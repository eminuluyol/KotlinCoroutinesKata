package com.taurus.kotlincoroutineskata.examples

import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlin.coroutines.CoroutineContext

abstract class ScopedFragment : Fragment(), CoroutineScope {

  private lateinit var job: Job

  override val coroutineContext: CoroutineContext
    get() = job + Dispatchers.Main

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    job = SupervisorJob()
  }

  override fun onStop() {
    coroutineContext.cancelChildren()
    super.onStop()
  }

}
