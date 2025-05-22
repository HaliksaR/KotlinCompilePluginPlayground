plugins {
    alias(libs.plugins.jvm)
    alias(libs.plugins.jgp)
    `maven-publish`
}

gradlePlugin {
    plugins {
        create("klasp") {
            id = "org.haliksar.klasp"
            implementationClass = "org.haliksar.klasp.plugin.gradle.KlaspPlugin"
        }
    }
}

dependencies {
    compileOnly(project(":compile"))
    implementation(libs.kgpApi)
}

tasks.register("sourcesJar", Jar::class) {
    group = "build"
    description = "Assembles Kotlin sources"

    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
    dependsOn(tasks.classes)
}

publishing {
    publications {
        create<MavenPublication>("klasp-gradle") {
            from(components["java"])
            artifact(tasks["sourcesJar"])

            groupId = "org.haliksar.klasp"
            version = "0.0.1"
            artifactId = "org.haliksar.klasp.gradle.plugin"
        }
    }
}