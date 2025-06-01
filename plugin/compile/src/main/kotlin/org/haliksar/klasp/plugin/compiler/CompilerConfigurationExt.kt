package org.haliksar.klasp.plugin.compiler

import org.jetbrains.kotlin.cli.common.config.kotlinSourceRoots
import org.jetbrains.kotlin.config.CompilerConfiguration

internal val CompilerConfiguration.place: String
    get() =
        kotlinSourceRoots.firstOrNull()
            ?.path
            ?.substringAfter("/src/")
            ?.substringBefore("/")
            .orEmpty()