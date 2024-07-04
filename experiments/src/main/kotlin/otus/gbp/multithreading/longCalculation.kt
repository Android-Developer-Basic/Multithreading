package otus.gbp.multithreading

import kotlin.system.measureTimeMillis

fun main() {
    val timeElapsed = measureTimeMillis {
        calculate()
    }
    println("Time elapsed: $timeElapsed")
}

private fun calculate() {
    println("Starting calculations...")
    Thread.sleep(200L)
    println("Done!")
}