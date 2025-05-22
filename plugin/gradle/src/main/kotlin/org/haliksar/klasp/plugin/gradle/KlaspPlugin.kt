package org.haliksar.klasp.plugin.gradle

import org.gradle.api.provider.Provider
import org.haliksar.klasp.plugin.compiler.*
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation
import org.jetbrains.kotlin.gradle.plugin.KotlinCompilerPluginSupportPlugin
import org.jetbrains.kotlin.gradle.plugin.SubpluginArtifact
import org.jetbrains.kotlin.gradle.plugin.SubpluginOption

class KlaspPlugin : KotlinCompilerPluginSupportPlugin {

    override fun applyToCompilation(kotlinCompilation: KotlinCompilation<*>): Provider<List<SubpluginOption>> =
        kotlinCompilation.target.project.provider {
            listOf(
                SubpluginOption(MODULE_NAME_OPTION, kotlinCompilation.target.project.displayName),
                SubpluginOption(
                    BUILD_DIR_OPTION,
                    kotlinCompilation.target.project.layout.buildDirectory.asFile.get().absolutePath
                ),
            )
        }

    override fun getCompilerPluginId(): String = ARTIFACT_ID

    override fun getPluginArtifact(): SubpluginArtifact =
        SubpluginArtifact(
            groupId = GROUP_ID,
            artifactId = ARTIFACT_ID,
            version = VERSION
        )

    override fun isApplicable(kotlinCompilation: KotlinCompilation<*>): Boolean =
        kotlinCompilation.target.project.plugins.hasPlugin(KlaspPlugin::class.java)
}