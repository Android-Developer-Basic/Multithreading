package otus.gbp.multithreading

import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() = runBlocking {
    log { "Делаем бургер..." }
    val time = measureTimeMillis {
        val bun = launch {
            kneadDough()
            bakeBuns()
        }
        val patty = launch {
            grindMeat()
            roastPatty()
        }
        joinAll(bun, patty)
        assemble()
    }
    log { "Готово! Время: $time" }
}

suspend fun perform(takesTime: Int = 1000, block: () -> String) {
    log { "Starting task: ${block()}" }
    repeat(takesTime / 100) {
        Thread.sleep(100)
    }
    log { "Finished task: ${block()}" }
}

suspend fun kneadDough() {
    perform(1000) {
        color(WHITE) {
            "Месим тесто"
        }
    }
}

suspend fun grindMeat() {
    perform(300) {
        color(RED) {
            "Рубим мясо"
        }
    }
}

suspend fun bakeBuns() {
    perform( 2000) {
        color(YELLOW) {
            "Печем булку"
        }
    }
}

suspend fun roastPatty() {
    perform( 1000) {
        color(PURPLE) {
            "Жарим котлету"
        }
    }
}

suspend fun assemble() {
    perform(1000) {
        color(GREEN) {
            "Собираем"
        }
    }
}