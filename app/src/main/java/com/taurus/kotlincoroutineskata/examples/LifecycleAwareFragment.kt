package com.taurus.kotlincoroutineskata.examples

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.taurus.kotlincoroutineskata.R
import com.taurus.kotlincoroutineskata.hide
import com.taurus.kotlincoroutineskata.visible
import kotlinx.android.synthetic.main.fragment_button.button
import kotlinx.android.synthetic.main.fragment_button.progressBar
import kotlinx.android.synthetic.main.fragment_button.textView
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Random
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext

class MainScope : CoroutineScope, LifecycleObserver {

  private lateinit var job: Job

  override val coroutineContext: CoroutineContext
    get() = job + Dispatchers.Main

  @OnLifecycleEvent(Lifecycle.Event.ON_START)
  fun onStart() {
    job = SupervisorJob()
  }

  @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
  fun onStop() = coroutineContext.cancelChildren()
}

class LifecycleAwareFragment : Fragment() {

  private val dataProvider = DataProvider()
  private val mainScope = MainScope()

  companion object {
    const val TAG = "LifecycleAwareFragment"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    lifecycle.addObserver(mainScope)
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_button, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    button.setOnClickListener { loadData() }
  }

  private fun loadData() = mainScope.launch {
    showLoading() // ui thread

    val result = dataProvider.loadData() // non ui thread, suspend until finished

    showText(result) // ui thread
    hideLoading() // ui thread
  }

  private fun showLoading() = progressBar.visible()

  private fun hideLoading() = progressBar.hide()

  private fun showText(data: String) {
    textView.text = data
  }

  class DataProvider(private val dispatcher: CoroutineDispatcher = Dispatchers.IO) {

    suspend fun loadData(): String = withContext(dispatcher) {
      delay(TimeUnit.SECONDS.toMillis(2)) // imitate long running operation
      "Data is available: ${Random().nextInt()}"
    }
  }
}
