package otus.gbp.multithreading

inline fun log(block: () -> String) {
    println("Thread: ${Thread.currentThread().name}: " + block())
}

const val RESET = "\u001B[0m"
const val BLACK = "\u001B[30m"
const val RED = "\u001B[31m"
const val GREEN = "\u001B[32m"
const val YELLOW = "\u001B[33m"
const val BLUE = "\u001B[34m"
const val PURPLE = "\u001B[35m"
const val CYAN = "\u001B[36m"
const val WHITE = "\u001B[37m"

inline fun color(color: String, block: () -> String): String {
    return "$color${block()}$RESET"
}
