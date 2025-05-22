package org.haliksar.klasp.plugin.compiler.fir

import org.jetbrains.kotlin.DeprecatedForRemovalCompilerApi
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter
import org.jetbrains.kotlin.fir.FirElement
import org.jetbrains.kotlin.fir.FirSession
import org.jetbrains.kotlin.fir.analysis.checkers.MppCheckerKind
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.DeclarationCheckers
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirFileChecker
import org.jetbrains.kotlin.fir.analysis.extensions.FirAdditionalCheckersExtension
import org.jetbrains.kotlin.fir.declarations.FirFile
import org.jetbrains.kotlin.fir.extensions.FirExtensionRegistrar
import org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
import org.jetbrains.kotlin.konan.file.File

@OptIn(DeprecatedForRemovalCompilerApi::class)
class Checker(private val buildDir: String) : FirFileChecker(MppCheckerKind.Common) {
    override fun check(declaration: FirFile, context: CheckerContext, reporter: DiagnosticReporter) {
        val visitor = TypeCollectorVisitor(buildDir)
        declaration.accept(visitor)
    }
}

class TypeCollectorVisitor(private val buildDir: String) : FirVisitorVoid() {
    override fun visitElement(element: FirElement) {
        File("$buildDir/out.txt")
            .apply {
                if (!exists) createNew()
                appendText(element::class.java.name)
            }
        element.acceptChildren(this)
    }
}

class TypeCollectorPlugin(private val buildDir: String) : FirExtensionRegistrar() {
    override fun ExtensionRegistrarContext.configurePlugin() {
        +FirAdditionalCheckersExtension.Factory {
            CheckersComponent(it, buildDir)
        }
    }
}

class CheckersComponent(session: FirSession, private val buildDir: String) : FirAdditionalCheckersExtension(session) {
    override val declarationCheckers: DeclarationCheckers = object : DeclarationCheckers() {
        override val fileCheckers: Set<FirFileChecker> = setOf(Checker(buildDir))
    }
}