import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main() {
    val counterContext = newSingleThreadContext("Counter Context")
    runBlocking {
        var counter = 0
        withContext(counterContext) {
            massiveRun { counter++ }
        }
        println("Updated counter value: $counter")
    }
}

private suspend fun massiveRun(action: suspend () -> Unit) {
    val n = 100
    val k = 1000
    val time = measureTimeMillis {
        coroutineScope {
            repeat(n) {
                launch {
                    repeat(k) {
                        action()
                    }
                }
            }
        }
    }
    println("Completed ${n*k} actions in time $time")
}