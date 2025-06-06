plugins {
    alias(libs.plugins.jvm)
    application
}

application {
    mainClass = "org.haliksar.app.AppKt"
}

kotlin {
    sourceSets {
        test {
            kotlin {
                srcDirs("src/unitTest/kotlin")
            }
        }
    }
}

dependencies {
    implementation(project(":feature:foo"))
    implementation(project(":feature:bar"))
    implementation(project(":shared:foo"))

    testImplementation(project(":feature:foo"))
    testImplementation(project(":feature:bar"))
    testImplementation(project(":shared:foo"))
}