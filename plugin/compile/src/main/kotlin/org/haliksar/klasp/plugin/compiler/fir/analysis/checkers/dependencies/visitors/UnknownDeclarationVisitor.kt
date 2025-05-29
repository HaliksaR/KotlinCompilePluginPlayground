package org.haliksar.klasp.plugin.compiler.fir.analysis.checkers.dependencies.visitors

import org.jetbrains.kotlin.fir.FirElement

internal class UnknownDeclarationVisitor(
    packageName: String,
    name: String,
) : DeclarationVisitor(packageName, name) {

    override fun visitElement(element: FirElement) {
        dependencies.add(element::class.java.name)
        super.visitElement(element)
    }
}