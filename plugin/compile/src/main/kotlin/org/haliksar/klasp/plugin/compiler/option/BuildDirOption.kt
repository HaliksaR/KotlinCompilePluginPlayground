package org.haliksar.klasp.plugin.compiler.option

import org.haliksar.klasp.plugin.compiler.BUILD_DIR_OPTION
import org.jetbrains.kotlin.compiler.plugin.CliOption
import org.jetbrains.kotlin.config.CompilerConfigurationKey

internal object BuildDirOption : Option<String> {
    override val name: String = BUILD_DIR_OPTION
    override val key = CompilerConfigurationKey<String>(name)
    override val option: CliOption = CliOption(
        optionName = name,
        valueDescription = "String",
        description = "build dir",
        required = true,
        allowMultipleOccurrences = false
    )
}