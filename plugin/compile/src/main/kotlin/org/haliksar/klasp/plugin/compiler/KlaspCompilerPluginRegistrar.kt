package org.haliksar.klasp.plugin.compiler

import org.haliksar.klasp.plugin.compiler.fir.TypeCollectorPlugin
import org.haliksar.klasp.plugin.compiler.ir.DependencyMapIrGenerationExtension
import org.haliksar.klasp.plugin.compiler.option.BuildDirOption
import org.haliksar.klasp.plugin.compiler.option.ModuleNameOption
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrarAdapter

@OptIn(ExperimentalCompilerApi::class)
class KlaspCompilerPluginRegistrar : CompilerPluginRegistrar() {
    override val supportsK2: Boolean = true

    override fun ExtensionStorage.registerExtensions(configuration: CompilerConfiguration) {
        val module = configuration.get(ModuleNameOption.key).orEmpty()
        val buildDir = configuration.get(BuildDirOption.key).orEmpty()
        val extension = DependencyMapIrGenerationExtension(module, buildDir)
        IrGenerationExtension.registerExtension(extension)
        FirExtensionRegistrarAdapter.registerExtension(TypeCollectorPlugin(buildDir))
    }
}