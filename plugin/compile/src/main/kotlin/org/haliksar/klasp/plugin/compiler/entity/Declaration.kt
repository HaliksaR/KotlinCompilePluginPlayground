package org.haliksar.klasp.plugin.compiler.entity

import kotlinx.serialization.Serializable

@Serializable
internal data class Declaration(
    val name: String,
    val dependencies: Set<String>
)