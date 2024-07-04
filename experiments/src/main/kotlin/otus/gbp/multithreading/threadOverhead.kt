package otus.gbp.multithreading

import java.util.concurrent.atomic.AtomicInteger
import kotlin.concurrent.thread
import kotlin.system.measureTimeMillis

fun main() {
    println("Main thread: ${measureTimeMillis { doInMain() }}")
    println("Other thread: ${measureTimeMillis { doInThread() }}")
}

private fun doInMain() {
    val counter = AtomicInteger(0)
    println("Doing in main thread...")
    (0..10_000).forEach {
        counter.incrementAndGet()
    }
    println("Counter: ${counter.get()}")
}

private fun doInThread() {
    val counter = AtomicInteger(0)
    println("Doing in other thread...")
    (0..10_000).forEach {
        thread { counter.incrementAndGet() }.join()
    }
    println("Counter: ${counter.get()}")
}