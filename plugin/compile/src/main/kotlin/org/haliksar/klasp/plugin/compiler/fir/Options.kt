package org.haliksar.klasp.plugin.compiler.fir

import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.extensions.FirExtensionSessionComponent

class Options(
    session: FirSession,
    val buildDir: String,
    val moduleName: String,
) : FirExtensionSessionComponent(session)

val FirSession.options: Options by FirSession.sessionComponentAccessor()