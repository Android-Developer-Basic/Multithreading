package otus.gpb.multithreading

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Runnable
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import otus.gpb.multithreading.databinding.ActivityMainBinding
import kotlin.time.Clock

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var th: BgThread? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
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
        log { "Sending message..." }
        th?.post {
            val currentTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time
            log { "Current time: $currentTime" }
            Thread.sleep(2000)
            Handler(Looper.getMainLooper()).post {
                log { "Logging current time..." }
                binding.message.text = getString(R.string.updated_at, currentTime)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        log { "onDestroy" }
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun log(block: () -> String) {
        val tag = "MainActivity"
        val message = "Thread: ${Thread.currentThread().name}, instance: ${System.identityHashCode(this).toHexString()}: " + block()
        if (isDestroyed) {
            Log.e(tag, "LEAKED ACTIVITY: $message")
        } else {
            Log.i(tag, message)
        }
    }

    private inner class BgThread : Thread() {
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

