package org.haliksar.klasp.plugin.compiler.ir

import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.ir.IrElement
import org.jetbrains.kotlin.ir.types.IrType
import org.jetbrains.kotlin.ir.types.classFqName
import org.jetbrains.kotlin.ir.visitors.IrTypeVisitorVoid

@Deprecated("FIR (Frontend IR) compiler is a new Kotlin Frontend")
class TypeVisitor(
    private val collector: MessageCollector
) : IrTypeVisitorVoid() {
    override fun visitType(container: IrElement, type: IrType) {
        collector.report(
            CompilerMessageSeverity.INFO,
            "${container::class.java.name} type ${type.classFqName?.asString()}"
        )
    }
}