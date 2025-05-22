package org.haliksar.klasp.plugin.compiler

import kotlinx.serialization.Serializable

@Serializable
internal data class Entity(
    val name: String,
    val type: String,
    val dependencies: MutableList<Entity> = mutableListOf()
)