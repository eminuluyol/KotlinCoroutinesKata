package com.taurus.kotlincoroutineskata.examples

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.taurus.kotlincoroutineskata.R
import com.taurus.kotlincoroutineskata.hide
import com.taurus.kotlincoroutineskata.visible
import kotlinx.android.synthetic.main.fragment_cancel.button
import kotlinx.android.synthetic.main.fragment_cancel.buttonCancel
import kotlinx.android.synthetic.main.fragment_cancel.progressBar
import kotlinx.android.synthetic.main.fragment_cancel.textView
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
import java.util.concurrent.CancellationException
import java.util.concurrent.TimeUnit

class CancelFragment : Fragment() {

  private val uiDispatcher: CoroutineDispatcher = Dispatchers.Main
  private val dataProvider = DataProvider()
  private var job = SupervisorJob()
  private val scope = CoroutineScope(uiDispatcher + job)

  companion object {
    const val TAG = "LaunchFragment"
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_cancel, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    button.setOnClickListener {
      // if you want to know how coroutine was completed, attach invokeOnCompletion
      loadData().invokeOnCompletion {
        if (it is CancellationException) { // if coroutine was cancelled
          textView.text = "Cancelled"
          hideLoading()
        }
      }
    }
    buttonCancel.setOnClickListener {
      // cancelling parent job will cancel all attached child coroutines
      scope.coroutineContext.cancelChildren()
      // we need to create new Job, because current is cancelled
      // new coroutine will not start, if parent Job is already cancelled
      job = SupervisorJob()
    }
  }

  override fun onStop() {
    job.cancel()
    super.onStop()
  }

  private fun loadData() = scope.launch {
    showLoading()

    val result = dataProvider.loadData()

    showText(result)
    hideLoading()
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
      delay(TimeUnit.SECONDS.toMillis(2)) // imitate long running operation
      "Data is available: ${Random().nextInt()}"
    }
  }
}
