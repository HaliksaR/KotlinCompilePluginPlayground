package org.haliksar.app

object Object
data object DObject

interface Interface
sealed interface SInterface
fun interface FunInterface : () -> Unit

abstract class AClass
annotation class Annotation

class Class
sealed class SClass

data class DClass(val value: String)

@JvmInline
value class VClass(val value: String)

enum class EClass

operator fun String.invoke() {

}

fun func() {

}

fun String.ext() {

}