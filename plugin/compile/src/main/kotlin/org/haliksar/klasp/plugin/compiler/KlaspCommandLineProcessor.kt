package org.haliksar.klasp.plugin.compiler

import org.haliksar.klasp.plugin.compiler.option.ModuleNameOption
import org.haliksar.klasp.plugin.compiler.option.RootBuildDirOption
import org.jetbrains.kotlin.compiler.plugin.AbstractCliOption
import org.jetbrains.kotlin.compiler.plugin.CommandLineProcessor
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.CompilerConfiguration

@OptIn(ExperimentalCompilerApi::class)
class KlaspCommandLineProcessor : CommandLineProcessor {

    override val pluginId = ARTIFACT_ID
    override val pluginOptions = listOf(
        ModuleNameOption.option,
        RootBuildDirOption.option,
    )

    override fun processOption(option: AbstractCliOption, value: String, configuration: CompilerConfiguration) {
        when (option.optionName) {
            ModuleNameOption.name -> configuration.put(ModuleNameOption.key, value)
            RootBuildDirOption.name -> configuration.put(RootBuildDirOption.key, value)
            else -> error("Unknown option: ${option.optionName}")
        }
    }
}