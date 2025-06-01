package org.haliksar.klasp.plugin.compiler

import org.haliksar.klasp.plugin.compiler.entity.Declaration
import org.haliksar.klasp.plugin.compiler.entity.Module
import org.haliksar.klasp.plugin.compiler.fir.KlaspFirExtensionRegistrar
import org.haliksar.klasp.plugin.compiler.option.ModuleNameOption
import org.haliksar.klasp.plugin.compiler.option.RootBuildDirOption
import org.jetbrains.kotlin.compiler.plugin.CompilerPluginRegistrar
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrarAdapter

@OptIn(ExperimentalCompilerApi::class)
class KlaspCompilerPluginRegistrar : CompilerPluginRegistrar() {

    override val supportsK2: Boolean = true

    override fun ExtensionStorage.registerExtensions(configuration: CompilerConfiguration) {
        val declarations: MutableSet<Declaration> = mutableSetOf()
        val extension = KlaspFirExtensionRegistrar(declarations)

        FirExtensionRegistrarAdapter.registerExtension(extension)

        registerDisposable {
            val moduleName = configuration.getNotNull(ModuleNameOption.key)
            val place = configuration.place
            val module = Module(
                moduleName,
                place,
                dependencies = declarations.flatMap(Declaration::dependencies).toSortedSet(),
                declarations = declarations.sortedBy(Declaration::name).toSet()
            )
            val logger = JsonLogger(
                moduleName,
                place,
                rootBuildDir = configuration.getNotNull(RootBuildDirOption.key)
            )
            logger(module)
        }
    }
}