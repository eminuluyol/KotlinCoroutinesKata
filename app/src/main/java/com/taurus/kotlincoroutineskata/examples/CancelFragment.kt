package com.taurus.kotlincoroutineskata.examples

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.taurus.kotlincoroutineskata.R
import com.taurus.kotlincoroutineskata.hide
import com.taurus.kotlincoroutineskata.visible
import kotlinx.android.synthetic.main.fragment_cancel.*
import kotlinx.coroutines.*
import java.util.*
import java.util.concurrent.CancellationException
import java.util.concurrent.TimeUnit

class CancelFragment : Fragment() {

    private val uiDispatcher: CoroutineDispatcher = Dispatchers.Main
    private val dataProvider = DataProvider()
    private lateinit var job: Job

    companion object {
        const val TAG = "LaunchFragment"
    }

    // Create the job

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_cancel, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button.setOnClickListener {
            // if you want to know how coroutine was completed, attach invokeOnCompletion
            // Check if coroutine was cancelled
        }
        buttonCancel.setOnClickListener {
            // cancelling parent job will cancel all attached child coroutines
            // we need to create new Job, because current is cancelled
            // new coroutine will not start, if parent Job is already cancelled
        }
    }

    // Cancel the job

    private fun loadData() = GlobalScope.launch(uiDispatcher + job) {
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

    class DataProvider(private val dispatcher: CoroutineDispatcher = Dispatchers.IO) {

        suspend fun loadData(): String = withContext(dispatcher) {
            delay(TimeUnit.SECONDS.toMillis(2)) // imitate long running operation
            "Data is available: ${Random().nextInt()}"
        }
    }
}
