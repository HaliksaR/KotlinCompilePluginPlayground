package org.haliksar.klasp.plugin.compiler.fir.analysis.checkers

import org.jetbrains.kotlin.fir.FirElement
import org.jetbrains.kotlin.fir.types.FirResolvedTypeRef
import org.jetbrains.kotlin.fir.types.classId
import org.jetbrains.kotlin.fir.types.forEachType
import org.jetbrains.kotlin.fir.visitors.FirVisitorVoid

internal class TypeVisitor(private val typeNameCollector: (String) -> Unit) : FirVisitorVoid() {

    override fun visitResolvedTypeRef(resolvedTypeRef: FirResolvedTypeRef) {
        resolvedTypeRef.coneType.forEachType { type ->
            type.classId?.asFqNameString()?.let { typeName ->
                typeNameCollector(typeName)
            }
        }
        super.visitResolvedTypeRef(resolvedTypeRef)
    }

    override fun visitElement(element: FirElement) {
        element.acceptChildren(this)
    }
}