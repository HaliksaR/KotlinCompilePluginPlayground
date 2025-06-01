package org.haliksar.klasp.plugin.compiler.entity

import kotlinx.serialization.Serializable

@Serializable
internal data class Module(
    val name: String,
    val place: String,
    val dependencies: Set<String>,
    val declarations: Set<Declaration>,
)