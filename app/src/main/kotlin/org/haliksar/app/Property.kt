package org.haliksar.app

const val ONE = "123"
const val CONST = ONE.get(1).code + 0

val value = calc()
val func = { _: Int, num: Number -> num.toString() }

val getter: String
    get() {
        val test = 1
        return test.toString()
    }

var setter = 1.0
    set(value) {
        val test = 12f

        fun calc() = CONST + 12

        field = test + value + calc()
    }

fun calc() = 1 + 1