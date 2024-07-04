package otus.gbp.multithreading

inline fun log(block: () -> String) {
    println("Thread: ${Thread.currentThread().name}: " + block())
}