package otus.gbp.multithreading

import java.util.concurrent.Executors

fun main() {
    val executor = Executors.newFixedThreadPool(2)
    repeat(10) {
        executor.execute {
            log { "Running a task..." }
        }
    }
    executor.shutdown()
}