package org.haliksar.klasp.plugin.compiler.fir

import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.extensions.FirExtensionSessionComponent

class Options(
    session: FirSession,
    val buildDir: String,
    val rootBuildDir: String,
    val moduleName: String,
) : FirExtensionSessionComponent(session) {

    operator fun component1() = buildDir
    operator fun component2() = rootBuildDir
    operator fun component3() = moduleName
}

val FirSession.options: Options by FirSession.sessionComponentAccessor()