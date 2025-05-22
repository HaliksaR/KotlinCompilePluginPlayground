package org.haliksar.klasp.plugin.compiler.ir

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.haliksar.klasp.plugin.compiler.Entity
import org.jetbrains.kotlin.backend.common.extensions.IrGenerationExtension
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.cli.common.messages.CompilerMessageSeverity
import org.jetbrains.kotlin.cli.common.messages.MessageCollector
import org.jetbrains.kotlin.ir.declarations.IrModuleFragment
import org.jetbrains.kotlin.ir.visitors.acceptVoid
import org.jetbrains.kotlin.konan.file.File

class DependencyMapIrGenerationExtension(
    private val module: String,
    private val buildDir: String
) : IrGenerationExtension {

    override fun generate(moduleFragment: IrModuleFragment, pluginContext: IrPluginContext) {
        val visitor = Visitor(module)
        moduleFragment.acceptVoid(visitor)
        pluginContext.messageCollector.report(CompilerMessageSeverity.INFO, "Dependency Map:\n")
        visitor.getTree().let { tree ->
            tree.print(pluginContext.messageCollector)
            File("$buildDir/$module-out.json")
                .apply {
                    val json = Json {
                        prettyPrint = true
                    }
                    if (!exists) createNew()
                    writeText(json.encodeToString(tree))
                }
        }
        moduleFragment.acceptVoid(TypeVisitor(pluginContext.messageCollector))
    }
}

internal fun Entity.print(collector: MessageCollector, deep: Int = 0) {
    val arrow = "\t".repeat(deep) + "->"
    collector.report(CompilerMessageSeverity.INFO, arrow + name + " : $type")
    dependencies.forEach {
        it.print(collector, deep + 1)
    }
}