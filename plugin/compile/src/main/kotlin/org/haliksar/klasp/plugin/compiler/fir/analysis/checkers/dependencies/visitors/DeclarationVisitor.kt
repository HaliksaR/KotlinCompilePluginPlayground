package org.haliksar.klasp.plugin.compiler.fir.analysis.checkers.dependencies.visitors

import org.haliksar.klasp.plugin.compiler.fir.analysis.checkers.dependencies.model.Declaration
import org.jetbrains.kotlin.fir.FirElement
import org.jetbrains.kotlin.fir.visitors.FirVisitorVoid

internal abstract class DeclarationVisitor(
    private val packageName: String,
    private val name: String
) : FirVisitorVoid() {

    protected val dependencies = mutableListOf<String>()

    val declaration: Declaration
        get() = Declaration(name = "$packageName.$name", dependencies)

    override fun visitElement(element: FirElement) {
        element.acceptChildren(this)
    }
}