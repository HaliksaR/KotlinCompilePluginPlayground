package org.haliksar.klasp.plugin.compiler.fir.analysis

import org.haliksar.klasp.plugin.compiler.fir.analysis.checkers.dependencies.DependencyGraph
import org.haliksar.klasp.plugin.compiler.fir.analysis.checkers.Logger
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirFileChecker
import org.jetbrains.kotlin.fir.analysis.extensions.FirAdditionalCheckersExtension

class FirAdditionalCheckers(session: FirSession) : FirAdditionalCheckersExtension(session) {
    override val declarationCheckers: DeclarationCheckers = object : DeclarationCheckers() {
        override val fileCheckers: Set<FirFileChecker> = setOf(Logger, DependencyGraph)
    }
}