package org.haliksar.klasp.plugin.compiler

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.haliksar.klasp.plugin.compiler.entity.Module
import java.io.File

internal class JsonLogger(
    private val moduleName: String,
    private val place: String,
    private val rootBuildDir: String,
) {

    operator fun invoke(module: Module) {
        val serializer = Json {
            prettyPrint = true
        }
        val json = serializer.encodeToString(module)

        createFileWithContent(
            filePath = "$rootBuildDir/deps/$moduleName:$place.json",
            content = json,
        )
    }

    private fun createFileWithContent(filePath: String, content: String): Boolean {
        val file = File(filePath)
        val parentDir = file.parentFile
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs()
        }
        return file.createNewFile().also {
            file.writeText(content)
        }
    }
}