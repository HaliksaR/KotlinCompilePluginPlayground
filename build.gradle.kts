import org.haliksar.klasp.plugin.gradle.KlaspPlugin

plugins {
    alias(libs.plugins.jvm)
    alias(libs.plugins.klasp) apply false
}

allprojects {
    apply<KlaspPlugin>()
}

kotlin {
    jvmToolchain(21)
}