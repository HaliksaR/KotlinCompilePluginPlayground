package org.haliksar.shared.foo

import kotlin.reflect.KClass

data class Entity(
    val name: String
)

interface Repository {

    fun <T> get(value: T): List<T>
}

object TextTagGroup {

    const val TEXT = "text"
}

@MyAnnotation(TextTagGroup::class)
fun Text(
    entity: Entity
) {
    val list = entity.name.map { it.code }.process {
        it.toString()
    }
    println(list + TextTagGroup.TEXT)
}

fun <T : Number> List<T>.process(action: (T) -> String): List<String> {
    return map(action)
}

annotation class MyAnnotation(val target: KClass<*>)