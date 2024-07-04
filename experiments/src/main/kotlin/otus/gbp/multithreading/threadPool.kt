package otus.gbp.multithreading

import kotlinx.coroutines.Runnable
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread
import kotlin.system.measureTimeMillis

fun main() {
    log { "Starting thread pool..." }
    val pool = ThreadPool(10)
    val counter = AtomicInteger(0)
    val time = measureTimeMillis {
        (0..10_000).forEach {
            pool.enqueue {
                counter.incrementAndGet()
            }
        }
        pool.start()
        println("Counter: ${counter.get()}")
    }
    println("Total time: $time")
}


class ThreadPool(threads: Int) {
    @Volatile
    private var running = true
    private val queue = ConcurrentLinkedQueue<Runnable>()
    private val threads = (1..threads).map {
        thread(start = false, name = "Thread-$it") {
            while (running) {
                queue.poll()?.run()
            }
        }
    }

    fun enqueue(runnable: Runnable) {
        if (running) queue.offer(runnable)
    }

    fun start() {
        threads.forEach { it.start() }
        while (true) {
            if (queue.isEmpty()) break
        }
        running = false
        threads.forEach { it.join() }
    }
}