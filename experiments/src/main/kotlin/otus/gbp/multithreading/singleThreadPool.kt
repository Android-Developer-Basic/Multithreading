package otus.gbp.multithreading

import java.util.concurrent.Executors

fun main() {
    val executor = Executors.newSingleThreadExecutor()
    repeat(10) {
        executor.execute {
            log { "Running a task..." }
        }
    }
    executor.shutdown()
}