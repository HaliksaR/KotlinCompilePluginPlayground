package org.haliksar.shared.foo

class FooRepository : Repository {

    private companion object {

        val COUNT = 5
    }

    override fun <T> get(value: T): List<T> =
        buildList {
            repeat(COUNT) {
                add(value)
            }
        }
}

object FooTagGroup {

    const val SCREEN = "ScreenFoo"
}

@MyAnnotation(FooTagGroup::class)
fun FooScreen(repository: Repository) {
    val entities = repository.get(Entity(name = config.id + FooTagGroup.SCREEN + multiplier(1)))
    entities.forEach(::Text)
}

interface SomeInterface {
    val id: String
    fun getData(): Int
}

// Анонимный объект
val config = object : SomeInterface {
    override val id: String = "config-id"
    override fun getData(): Int = 42
}

// Анонимная функция
val multiplier = fun(x: Int): Int {
    return x * 3
}
