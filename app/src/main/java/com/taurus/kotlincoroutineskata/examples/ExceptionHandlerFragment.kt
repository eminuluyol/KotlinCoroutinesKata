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
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Random
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext

class ExceptionHandlerFragment : Fragment() {

  private val uiDispatcher: CoroutineDispatcher = Dispatchers.Main
  private val dataProvider = DataProvider()
  private val exceptionHandler: CoroutineContext = CoroutineExceptionHandler { _, throwable ->
    showText(throwable.message ?: "")
    hideLoading()
    job = SupervisorJob() // exception handler cancels job
  }
  private var job = SupervisorJob()
  private val scope = CoroutineScope(uiDispatcher + exceptionHandler + job)

  companion object {
    const val TAG = "ExceptionHandlerFragment"
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_button, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    button.setOnClickListener { loadData() }
  }

  // we can attach CoroutineExceptionHandler to parent context
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
      mayThrowException()
      "Data is available: ${Random().nextInt()}"
    }

    private fun mayThrowException() {
      if (Random().nextBoolean()) {
        throw IllegalArgumentException("Ooops exception occurred")
      }
    }
  }
}
