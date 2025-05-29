package org.haliksar.klasp.plugin.compiler.fir.analysis.checkers.dependencies.visitors

import org.jetbrains.kotlin.fir.declarations.FirTypeParameter
import org.jetbrains.kotlin.fir.scopes.impl.toConeType
import org.jetbrains.kotlin.fir.types.*

internal class TypeAliasDeclarationVisitor(
    packageName: String,
    name: String,
) : DeclarationVisitor(packageName, name) {

    override fun visitTypeParameter(typeParameter: FirTypeParameter) {
        typeParameter.toConeType().visit()
    }

    override fun visitResolvedTypeRef(resolvedTypeRef: FirResolvedTypeRef) {
        resolvedTypeRef.coneType.visit()
    }

    private fun ConeTypeProjection.visit() {
        addType(this)
        type?.typeArguments?.forEach { type ->
            type.type?.visit()
        }
    }

    private fun addType(type: ConeTypeProjection) {
        when (type) {
            is ConeDefinitelyNotNullType -> {
                dependencies.add("ConeDefinitelyNotNullType$type")
            }

            is ConeIntersectionType -> {
                dependencies.add("ConeIntersectionType$type")
            }

            is ConeDynamicType -> {
                dependencies.add("ConeDynamicType$type")
            }

            is ConeFlexibleType -> {
                dependencies.add("ConeFlexibleType$type")
            }

            is ConeSimpleKotlinType -> {
                type.classId?.asFqNameString()?.let {
                    dependencies.add(it)
                }
            }

            is ConeKotlinTypeConflictingProjection -> {
                dependencies.add("ConeKotlinTypeConflictingProjection$type")
            }

            is ConeKotlinTypeProjectionIn -> {
                dependencies.add("ConeKotlinTypeProjectionIn$type")
            }

            is ConeKotlinTypeProjectionOut -> {
                dependencies.add("ConeKotlinTypeProjectionOut$type")
            }

            ConeStarProjection -> {
                dependencies.add("ConeStarProjection$type")
            }
        }
    }
}