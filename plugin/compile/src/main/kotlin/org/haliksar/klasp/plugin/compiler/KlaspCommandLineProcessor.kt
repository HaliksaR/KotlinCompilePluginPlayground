package org.haliksar.klasp.plugin.compiler

import org.haliksar.klasp.plugin.compiler.option.BuildDirOption
import org.haliksar.klasp.plugin.compiler.option.ModuleNameOption
import org.jetbrains.kotlin.compiler.plugin.AbstractCliOption
import org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.CompilerConfiguration

@OptIn(ExperimentalCompilerApi::class)
class KlaspCommandLineProcessor : CommandLineProcessor {

    override val pluginId = ARTIFACT_ID
    override val pluginOptions = listOf(ModuleNameOption.option, BuildDirOption.option)

    override fun processOption(option: AbstractCliOption, value: String, configuration: CompilerConfiguration) {
        when (option.optionName) {
            ModuleNameOption.name -> configuration.put(ModuleNameOption.key, value)
            BuildDirOption.name -> configuration.put(BuildDirOption.key, value)
            else -> error("Unknown option: ${option.optionName}")
        }
    }
}