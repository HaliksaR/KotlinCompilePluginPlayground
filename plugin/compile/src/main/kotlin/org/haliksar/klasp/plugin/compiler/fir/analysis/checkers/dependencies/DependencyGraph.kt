package org.haliksar.klasp.plugin.compiler.fir.analysis.checkers.dependencies

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.haliksar.klasp.plugin.compiler.fir.analysis.checkers.dependencies.visitors.*
import org.haliksar.klasp.plugin.compiler.fir.options
import org.jetbrains.kotlin.DeprecatedForRemovalCompilerApi
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter
import org.jetbrains.kotlin.fir.analysis.checkers.MppCheckerKind
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirFileChecker
import org.jetbrains.kotlin.fir.declarations.*
import org.jetbrains.kotlin.konan.file.File

@OptIn(DeprecatedForRemovalCompilerApi::class)
object DependencyGraph : FirFileChecker(MppCheckerKind.Common) {

    private val json = Json {
        prettyPrint = true
    }

    @OptIn(DirectDeclarationsAccess::class)
    override fun check(declaration: FirFile, context: CheckerContext, reporter: DiagnosticReporter) {
        val (_, rootBuildDir, _) = context.session.options

        val packageName = declaration.packageDirective.packageFqName.asString()

        val output = File("$rootBuildDir/$packageName.${declaration.name}.json")
            .apply {
                if (!exists) createNew()
            }

        val declarations = declaration.declarations.map { declaration ->
            val visitor = getDeclarationVisitor(packageName, declaration)
            declaration.acceptChildren(visitor)
            visitor.declaration
        }

        output.writeText(json.encodeToString(declarations))
    }

    private fun getDeclarationVisitor(packageName: String, declaration: FirDeclaration): DeclarationVisitor {
        val (name, constructor) = when (declaration) {
            is FirSimpleFunction -> declaration.name.asString() to ::FunctionDeclarationVisitor
            is FirRegularClass -> declaration.name.asString() to ::ClassDeclarationVisitor
            is FirProperty -> declaration.name.asString() to ::PropertyDeclarationVisitor
            is FirTypeAlias -> declaration.name.asString() to ::TypeAliasDeclarationVisitor
            else -> declaration::class.java.name to ::UnknownDeclarationVisitor
        }
        return constructor(packageName, name)
    }
}