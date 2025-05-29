package org.haliksar.klasp.plugin.compiler.fir.analysis.checkers.dependencies.model

import kotlinx.serialization.Serializable

@Serializable
data class Declaration(
    val name: String,
    val dependencies: List<String>
)