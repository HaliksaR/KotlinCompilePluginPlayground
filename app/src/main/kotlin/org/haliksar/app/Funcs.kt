package org.haliksar.app

import java.text.DecimalFormat
import java.util.*

val date = "1234"

class Fuu {

    companion object {

        val test = { Date(1234) }
    }

    val test = { Date(1234) }

    fun foo(): Long {
        val (one, two) = arrayOf(123) to DecimalFormat().format(123)
        fun calc() = test().time + 124L

        val test2 = object {
            private val test2 = test

            fun tesy(param: List<*>, param2: Collection<HashMap<Nothing, () -> Unit>>) = buildString {
                append(TODO()::class.java.name)
            }
        }

        return date.test().code + calc()
    }
}

fun foo(): Long {
    val test = { Date(1234) }
    val (one, two) = arrayOf(123) to DecimalFormat().format(123)
    fun calc() = test().time + 124L

    val test2 = object {
        private val test = test

        fun tesy(param: List<*>, param2: Collection<HashMap<Nothing, () -> Unit>>) = buildString {
            append(TODO()::class.java.name)
        }
    }

    return date.test().code + calc()
}

fun String.test() = get(124)

fun <T : Any> rec(): T =
    rec()