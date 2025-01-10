package otus.gpb.multithreading

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Runnable
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import otus.gpb.multithreading.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var th: BgThread? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            start.setOnClickListener {
                start.isEnabled = false
                send.isEnabled = true
                startThread()
            }
            send.setOnClickListener {
                sendMessage()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        th?.terminate()
    }

    private fun startThread() {
         th = BgThread().apply { start() }
    }

    private fun sendMessage() {
        th?.post {
            log { "Current time: ${Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time}" }
        }
    }

    private class BgThread : Thread() {
        private lateinit var handler: Handler

        init {
            this.name = "BgThread"
        }

        override fun run() {
            super.run()
            log { "Running thread..." }
            Looper.prepare()
            handler = Handler(requireNotNull(Looper.myLooper()) { "Should have a looper" })
            Looper.loop()
            log { "Thread complete" }
        }

        fun post(runnable: Runnable) {
            handler.post(runnable)
        }

        fun terminate() {
            post {
                Looper.myLooper()?.quit()
            }
        }
    }
}

private fun log(block: () -> String) {
    Log.i("MainActivity", "Thread: ${Thread.currentThread().name}: " + block())
}

