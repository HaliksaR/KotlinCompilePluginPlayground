package org.haliksar.klasp.plugin.compiler

import org.haliksar.klasp.plugin.compiler.fir.TypeCollectorPlugin
import org.haliksar.klasp.plugin.compiler.option.BuildDirOption
import org.haliksar.klasp.plugin.compiler.option.ModuleNameOption
import org.haliksar.klasp.plugin.compiler.option.RootBuildDirOption
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.messageCollector
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrarAdapter

@OptIn(ExperimentalCompilerApi::class)
class KlaspCompilerPluginRegistrar : CompilerPluginRegistrar() {
    override val supportsK2: Boolean = true

    override fun ExtensionStorage.registerExtensions(configuration: CompilerConfiguration) {
        val module = configuration.getNotNull(ModuleNameOption.key)
        val buildDir = configuration.getNotNull(BuildDirOption.key)
        val rootBuildDir = configuration.getNotNull(RootBuildDirOption.key)
        configuration.messageCollector.report(CompilerMessageSeverity.INFO, "hello")
        val extension = TypeCollectorPlugin(buildDir, rootBuildDir, module)
        FirExtensionRegistrarAdapter.registerExtension(extension)
    }
}