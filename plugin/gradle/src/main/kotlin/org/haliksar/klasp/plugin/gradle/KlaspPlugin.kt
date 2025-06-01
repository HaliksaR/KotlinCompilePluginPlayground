package org.haliksar.klasp.plugin.gradle

import org.gradle.api.provider.Provider
import org.haliksar.klasp.plugin.compiler.*
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilerPluginSupportPlugin
import org.jetbrains.kotlin.gradle.plugin.SubpluginArtifact
import org.jetbrains.kotlin.gradle.plugin.SubpluginOption

class KlaspPlugin : KotlinCompilerPluginSupportPlugin {

    override fun applyToCompilation(kotlinCompilation: KotlinCompilation<*>): Provider<List<SubpluginOption>> {
        val moduleName = kotlinCompilation.project.path
        val rootBuildDir = kotlinCompilation.project.rootProject.layout.buildDirectory.asFile.get().absolutePath

        return kotlinCompilation.project.provider {
            listOf(
                SubpluginOption(MODULE_NAME_OPTION, moduleName),
                SubpluginOption(ROOT_BUILD_DIR_OPTION, rootBuildDir),
            )
        }
    }

    override fun getCompilerPluginId(): String = ARTIFACT_ID

    override fun getPluginArtifact(): SubpluginArtifact =
        SubpluginArtifact(
            groupId = GROUP_ID,
            artifactId = ARTIFACT_ID,
            version = VERSION
        )

    override fun isApplicable(kotlinCompilation: KotlinCompilation<*>): Boolean =
        kotlinCompilation.project.plugins.hasPlugin(KlaspPlugin::class.java)
}