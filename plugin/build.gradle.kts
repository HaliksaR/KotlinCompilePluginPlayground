plugins {
    alias(libs.plugins.jvm)
    alias(libs.plugins.serialization) apply false
}

kotlin {
    jvmToolchain(21)
}