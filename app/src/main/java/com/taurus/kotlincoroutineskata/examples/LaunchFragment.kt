package com.taurus.kotlincoroutineskata.examples

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.taurus.kotlincoroutineskata.R
import com.taurus.kotlincoroutineskata.hide
import com.taurus.kotlincoroutineskata.visible
import kotlinx.android.synthetic.main.fragment_button.button
import kotlinx.android.synthetic.main.fragment_button.progressBar
import kotlinx.android.synthetic.main.fragment_button.textView
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Random
import java.util.concurrent.TimeUnit

class LaunchFragment : Fragment() {

  private val uiDispatcher: CoroutineDispatcher = Dispatchers.Main
  private val dataProvider = DataProvider()
  private val job = SupervisorJob()
  private val scope = CoroutineScope(uiDispatcher + job)

  companion object {
    const val TAG = "LaunchFragment"
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_button, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    button.setOnClickListener { loadData() }
  }

  private fun loadData() = scope.launch {
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

  override fun onDestroyView() {
    scope.coroutineContext.cancelChildren()
    super.onDestroyView()
  }

  class DataProvider(private val dispatcher: CoroutineDispatcher = Dispatchers.IO) {

    suspend fun loadData(): String = withContext(dispatcher) {
      delay(TimeUnit.SECONDS.toMillis(4)) // imitate long running operation
      "Data is available: ${Random().nextInt()}"
    }
  }
}
