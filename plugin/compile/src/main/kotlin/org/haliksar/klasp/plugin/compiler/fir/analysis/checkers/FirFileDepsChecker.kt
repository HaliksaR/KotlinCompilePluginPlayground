package org.haliksar.klasp.plugin.compiler.fir.analysis.checkers

import org.haliksar.klasp.plugin.compiler.fir.options
import org.jetbrains.kotlin.DeprecatedForRemovalCompilerApi
import org.jetbrains.kotlin.diagnostics.DiagnosticReporter
import org.jetbrains.kotlin.fir.FirElement
import org.jetbrains.kotlin.fir.analysis.checkers.MppCheckerKind
import org.jetbrains.kotlin.fir.analysis.checkers.context.CheckerContext
import org.jetbrains.kotlin.fir.analysis.checkers.declaration.FirFileChecker
import org.jetbrains.kotlin.fir.declarations.FirFile
import org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
import org.jetbrains.kotlin.konan.file.File

@OptIn(DeprecatedForRemovalCompilerApi::class)
object FirFileDepsChecker : FirFileChecker(MppCheckerKind.Common) {

    override fun check(declaration: FirFile, context: CheckerContext, reporter: DiagnosticReporter) {
        val buildDir = context.session.options.buildDir
        val moduleName = context.session.options.moduleName

        val logs = buildList {
            add(moduleName)
            val visitor = TypeCollectorVisitor(this)
            declaration.accept(visitor)
        }
        File("$buildDir/$moduleName-out.log")
            .apply {
                if (!exists) createNew()
                writeLines(logs)
            }
    }
}

class TypeCollectorVisitor(
    private val logs: MutableList<String>,
) : FirVisitorVoid() {

    private var deep = 0
    val arrow: String
        get() = "\t".repeat(deep) + "->"

    override fun visitElement(element: FirElement) {
        deep++
        val properties = element::class.members.joinToString("\n") {
            "\t".repeat(deep + 1) + it.name + " : " + runCatching { it.call(element) }.getOrNull()
        }
        logs.add("$arrow${element}\n$properties")
        element.acceptChildren(this)
        deep--
    }

    override fun visitFile(file: FirFile) {
        logs.add("$arrow${file.sourceFile?.path}")
        super.visitFile(file)
    }
}