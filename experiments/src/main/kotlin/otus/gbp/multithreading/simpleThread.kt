package otus.gbp.multithreading

import kotlin.concurrent.thread

fun main() {
    log { "Starting thread..." }
    val thread = thread(start = true, name = "Worker") {
        log { "Thread task" }
    }
    thread.join()
    log { "Task complete" }
}