package com.taurus.kotlincoroutineskata.examples

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.taurus.kotlincoroutineskata.R
import com.taurus.kotlincoroutineskata.hide
import com.taurus.kotlincoroutineskata.visible
import kotlinx.android.synthetic.main.fragment_button.*
import kotlinx.coroutines.*
import java.util.*
import java.util.concurrent.TimeUnit

class LaunchSequentiallyFragment : Fragment() {

    private val uiDispatcher: CoroutineDispatcher = Dispatchers.Main
    private val dataProvider = DataProvider()
    private lateinit var job: Job

    companion object {
        const val TAG = "LaunchSequentiallyFragment"
    }

    // Create the job

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_button, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button.setOnClickListener {
            // loadData()
        }
    }

    private fun loadData()  {
        // show loading
        // get the first result from data provider
        // get the second result from data provider
        // show Text(Both of them one under the other)
        // hide loading
    }

    // Cancel the job

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
