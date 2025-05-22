package org.haliksar.klasp.plugin.compiler

import org.haliksar.klasp.plugin.compiler.fir.TypeCollectorPlugin
import org.haliksar.klasp.plugin.compiler.option.BuildDirOption
import org.haliksar.klasp.plugin.compiler.option.ModuleNameOption
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrarAdapter

@OptIn(ExperimentalCompilerApi::class)
class KlaspCompilerPluginRegistrar : CompilerPluginRegistrar() {
    override val supportsK2: Boolean = true

    override fun ExtensionStorage.registerExtensions(configuration: CompilerConfiguration) {
        val module = configuration.getNotNull(ModuleNameOption.key)
        val buildDir = configuration.getNotNull(BuildDirOption.key)

        val extension = TypeCollectorPlugin(buildDir, module)
        FirExtensionRegistrarAdapter.registerExtension(extension)
    }
}