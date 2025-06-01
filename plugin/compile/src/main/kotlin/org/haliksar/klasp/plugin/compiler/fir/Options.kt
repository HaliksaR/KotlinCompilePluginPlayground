package org.haliksar.klasp.plugin.compiler.fir

import org.haliksar.klasp.plugin.compiler.entity.Declaration
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.extensions.FirExtensionSessionComponent

internal class Options(
    session: FirSession,
    private val declarations: MutableSet<Declaration>,
) : FirExtensionSessionComponent(session) {

    fun addDeclarations(elements: Collection<Declaration>) {
        declarations.addAll(elements)
    }
}

internal val FirSession.options: Options by FirSession.sessionComponentAccessor()