plugins {
    alias(libs.plugins.jvm)
    alias(libs.plugins.serialization)
    `maven-publish`
}

dependencies {
    compileOnly(libs.embeddable)
    implementation(libs.serialization)
}

publishing {
    publications {
        create<MavenPublication>("klasp-compiler") {
            from(components["java"])

            groupId = "org.haliksar.klasp"
            version = "0.0.1"
            artifactId = "org.haliksar.klasp.compiler.plugin"
        }
    }
}
