package org.haliksar.klasp.plugin.compiler.option

import org.haliksar.klasp.plugin.compiler.MODULE_NAME_OPTION
import org.jetbrains.kotlin.compiler.plugin.CliOption
import org.jetbrains.kotlin.config.CompilerConfigurationKey

internal object ModuleNameOption : Option<String> {
    override val name: String = MODULE_NAME_OPTION
    override val key = CompilerConfigurationKey<String>(name)
    override val option: CliOption = CliOption(
        optionName = name,
        valueDescription = "String",
        description = "gradle module name",
        required = true,
        allowMultipleOccurrences = false
    )
}