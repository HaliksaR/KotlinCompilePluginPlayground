package org.haliksar.klasp.plugin.compiler.fir.ext

import org.jetbrains.kotlin.fir.declarations.*

internal val FirDeclaration.name: String
    get() = when (this) {
        is FirSimpleFunction -> name.asString()
        is FirRegularClass -> name.asString()
        is FirProperty -> name.asString()
        is FirTypeAlias -> name.asString()
        else -> this::class.java.name
    }
