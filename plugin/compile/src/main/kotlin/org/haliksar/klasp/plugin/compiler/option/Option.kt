package org.haliksar.klasp.plugin.compiler.option

import org.jetbrains.kotlin.compiler.plugin.CliOption
import org.jetbrains.kotlin.config.CompilerConfigurationKey

internal interface Option<T> {
    val name: String
    val key: CompilerConfigurationKey<T>
    val option: CliOption
}