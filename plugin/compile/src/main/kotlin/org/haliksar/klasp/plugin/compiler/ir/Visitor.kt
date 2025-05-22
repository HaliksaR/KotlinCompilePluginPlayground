package org.haliksar.klasp.plugin.compiler.ir

import org.haliksar.klasp.plugin.compiler.Entity
import org.jetbrains.kotlin.ir.IrElement
import org.jetbrains.kotlin.ir.declarations.*
import org.jetbrains.kotlin.ir.expressions.*
import org.jetbrains.kotlin.ir.symbols.UnsafeDuringIrConstructionAPI
import org.jetbrains.kotlin.ir.types.classFqName
import org.jetbrains.kotlin.ir.util.fqNameWhenAvailable
import org.jetbrains.kotlin.ir.visitors.IrVisitorVoid
import org.jetbrains.kotlin.ir.visitors.acceptChildrenVoid

@Deprecated("FIR (Frontend IR) compiler is a new Kotlin Frontend")
internal class Visitor(private val module: String) : IrVisitorVoid() {

    private lateinit var last: Entity

    fun getTree(): Entity = last

    override fun visitModuleFragment(declaration: IrModuleFragment) {
        val root = Entity(module, IrModuleFragment::class.java.name)
        last = root
        super.visitModuleFragment(declaration)
        last = root
    }

    override fun visitFile(declaration: IrFile) {
        val file = Entity(declaration.nameWithPackage, IrFile::class.java.name)
        last.dependencies.add(file)
        val lastTemp = last
        last = file
        super.visitFile(declaration)
        last = lastTemp
    }

    override fun visitElement(element: IrElement) {
        last.dependencies.add(Entity("uncatch", element::class.java.name))
        element.acceptChildrenVoid(this)
    }

    // Анализ классов (включая вложенные и внутренние)
    @OptIn(UnsafeDuringIrConstructionAPI::class)
    override fun visitClass(declaration: IrClass) {
        val className = declaration.fqNameWhenAvailable?.asString() ?: return
        val entity = Entity(className, IrClass::class.java.name)
        last.dependencies.add(entity)
        val lastTemp = last
        last = entity

        // Супертипы и интерфейсы
        declaration.superTypes.forEach { superType ->
            val superTypeName = superType.classFqName?.asString()
            if (superTypeName != null && superTypeName != "kotlin.Any") {
                last.dependencies.add(Entity(superTypeName, IrElement::class.java.name))
            }
        }
        super.visitClass(declaration)
        last = lastTemp
    }

    override fun visitProperty(declaration: IrProperty) {
        val typeName = declaration.backingField?.type?.classFqName?.asString() ?: return
        val entity = Entity(typeName, IrProperty::class.java.name)
        last.dependencies.add(entity)
        val temp = last
        last = entity
        super.visitProperty(declaration)
        last = temp
    }

    override fun visitField(declaration: IrField) {
        super.visitField(declaration)
    }

    override fun visitExpressionBody(body: IrExpressionBody) {
        super.visitExpressionBody(body)
    }

    override fun visitConst(expression: IrConst) {
        super.visitConst(expression)
    }

    // Анализ функций (включая лямбды и дженерики)
    @OptIn(UnsafeDuringIrConstructionAPI::class)
    override fun visitFunction(declaration: IrFunction) {
        val functionName = declaration.fqNameWhenAvailable?.asString() ?: return
        val entity = Entity(functionName, IrFunction::class.java.name)
        last.dependencies.add(entity)
        val lastTemp = last
        last = entity

        // Возвращаемый тип
        val returnTypeName = declaration.returnType.classFqName?.asString()
        if (returnTypeName != null) {
            last.dependencies.add(Entity(returnTypeName, IrElement::class.java.name))
        }

        super.visitFunction(declaration)
        last = lastTemp
    }

    override fun visitTypeParameter(declaration: IrTypeParameter) {
        declaration.superTypes.forEach { superType ->
            superType.classFqName?.asString()?.let {
                last.dependencies.add(Entity(it, IrTypeParameter::class.java.name))
            }
        }
    }

    @OptIn(UnsafeDuringIrConstructionAPI::class)
    override fun visitCall(expression: IrCall) {
        val calledFunctionName = expression.symbol.owner.fqNameWhenAvailable?.asString() ?: return
        val entity = Entity(calledFunctionName, IrCall::class.java.name)
        last.dependencies.add(entity)
        val temp = last
        last = entity
        super.visitCall(expression)
        last = temp
    }

    override fun visitFunctionExpression(expression: IrFunctionExpression) {
        val lambdaType = expression.function.returnType.classFqName?.asString() ?: return
        val entity = Entity(lambdaType, IrFunctionExpression::class.java.name)
        last.dependencies.add(entity)
        val temp = last
        last = entity
        super.visitFunctionExpression(expression)
        last = temp
    }

    override fun visitSimpleFunction(declaration: IrSimpleFunction) {
        super.visitSimpleFunction(declaration)
    }

    override fun visitValueParameter(declaration: IrValueParameter) {
        val paramType = declaration.type.classFqName?.asString() ?: return
        val entity = Entity(paramType, IrValueParameter::class.java.name)
        last.dependencies.add(entity)
        val temp = last
        last = entity
        super.visitValueParameter(declaration)
        last = temp
    }

    override fun visitConstructor(declaration: IrConstructor) {
        super.visitConstructor(declaration)
    }

    override fun visitBlockBody(body: IrBlockBody) {
        super.visitBlockBody(body)
    }

    override fun visitReturn(expression: IrReturn) {
        super.visitReturn(expression)
    }

    override fun visitGetField(expression: IrGetField) {
        super.visitGetField(expression)
    }

    override fun visitGetValue(expression: IrGetValue) {
        super.visitGetValue(expression)
    }

    override fun visitDelegatingConstructorCall(expression: IrDelegatingConstructorCall) {
        super.visitDelegatingConstructorCall(expression)
    }

    override fun visitInstanceInitializerCall(expression: IrInstanceInitializerCall) {
        super.visitInstanceInitializerCall(expression)
    }
}