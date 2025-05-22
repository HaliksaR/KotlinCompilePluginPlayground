plugins {
    alias(libs.plugins.jvm)
}

dependencies {
    implementation(project(":shared:foo"))
}