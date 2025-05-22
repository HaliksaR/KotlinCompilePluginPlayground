package org.haliksar.feature.bar

import org.haliksar.shared.foo.Entity
import org.haliksar.shared.foo.MyAnnotation
import org.haliksar.shared.foo.Repository
import org.haliksar.shared.foo.Text


private val COUNT = 5

object BarRepository : Repository {

    override fun <T> get(value: T): List<T> =
        buildList {
            repeat(COUNT) {
                add(value)
            }
        }
}

object BarTagGroup {

    const val SCREEN = "ScreenBar"
}

@MyAnnotation(BarTagGroup::class)
fun BarScreen(repository: Repository) {
    val entities = repository.get(Entity(name = "foo" + BarTagGroup.SCREEN))
    entities.forEach(::Text)
}