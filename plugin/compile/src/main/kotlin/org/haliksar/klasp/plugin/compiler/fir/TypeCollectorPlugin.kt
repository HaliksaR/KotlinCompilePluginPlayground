package org.haliksar.klasp.plugin.compiler.fir

import org.haliksar.klasp.plugin.compiler.fir.analysis.FirAdditionalCheckers
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar
import org.jetbrains.kotlin.fir.extensions.FirExtensionSessionComponent

class TypeCollectorPlugin(
    private val buildDir: String,
    private val rootBuildDir: String,
    private val moduleName: String,
) : FirExtensionRegistrar() {
    override fun ExtensionRegistrarContext.configurePlugin() {
        +FirExtensionSessionComponent.Factory {
            Options(
                it,
                buildDir,
                rootBuildDir,
                moduleName
            )
        }
        +::FirAdditionalCheckers
    }
}