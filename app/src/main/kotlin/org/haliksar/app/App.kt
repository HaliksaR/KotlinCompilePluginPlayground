package org.haliksar.app

import org.haliksar.feature.bar.BarRepository
import org.haliksar.feature.bar.BarScreen
import org.haliksar.shared.foo.FooRepository
import org.haliksar.shared.foo.FooScreen
import org.haliksar.shared.foo.Repository

fun main() {
    val fooRepository = FooRepository()
    val barRepository = BarRepository
}

private fun Application(
    fooRepository: Repository,
    barRepository: Repository,
) {
    FooScreen(fooRepository)
    BarScreen(barRepository)
}