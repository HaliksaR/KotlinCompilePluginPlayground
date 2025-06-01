package org.haliksar.klasp.plugin.compiler.fir.analysis.checkers

import org.haliksar.klasp.plugin.compiler.entity.Declaration
import org.haliksar.klasp.plugin.compiler.fir.ext.name
import org.haliksar.klasp.plugin.compiler.fir.options
import org.jetbrains.kotlin.DeprecatedForRemovalCompilerApi
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter
import org.jetbrains.kotlin.fir.analysis.checkers.MppCheckerKind
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirFileChecker
import org.jetbrains.kotlin.fir.declarations.DirectDeclarationsAccess
import org.jetbrains.kotlin.fir.declarations.FirFile

internal object KlaspFirFileChecker : FirFileChecker(MppCheckerKind.Common) {

    @OptIn(DeprecatedForRemovalCompilerApi::class, DirectDeclarationsAccess::class)
    override fun check(declaration: FirFile, context: CheckerContext, reporter: DiagnosticReporter) {
        val options = context.session.options

        val packageName = declaration.packageDirective.packageFqName.asString()

        val dependencies = mutableSetOf<String>()
        val visitor = TypeVisitor(dependencies::add)

        val declarations = declaration.declarations.map { declaration ->
            Declaration(
                name = "$packageName.${declaration.name}",
                dependencies = run {
                    dependencies.clear()
                    declaration.acceptChildren(visitor)
                    dependencies.toSortedSet()
                },
            )
        }

        options.addDeclarations(declarations)
    }
}