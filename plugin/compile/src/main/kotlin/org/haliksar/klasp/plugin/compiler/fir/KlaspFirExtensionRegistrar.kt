package org.haliksar.klasp.plugin.compiler.fir

import org.haliksar.klasp.plugin.compiler.entity.Declaration
import org.haliksar.klasp.plugin.compiler.fir.analysis.FirAdditionalCheckers
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar
import org.jetbrains.kotlin.fir.extensions.FirExtensionSessionComponent

internal class KlaspFirExtensionRegistrar(
    declarations: MutableSet<Declaration>,
) : FirExtensionRegistrar() {

    private val options = FirExtensionSessionComponent.Factory { session ->
        Options(session, declarations)
    }

    override fun ExtensionRegistrarContext.configurePlugin() {
        +options
        +::FirAdditionalCheckers
    }
}