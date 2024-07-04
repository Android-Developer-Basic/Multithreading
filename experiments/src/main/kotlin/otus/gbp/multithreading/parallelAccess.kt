package otus.gbp.multithreading

import kotlin.system.measureTimeMillis

fun main() {
    log { "Starting thread pool..." }
    val pool = ThreadPool(10)
    var counter = 0
    val time = measureTimeMillis {
        (0..10_000).forEach {
            pool.enqueue {
                ++counter
            }
        }
        pool.start()
        println("Counter: $counter")
    }
}