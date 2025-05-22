@file:Suppress("UnstableApiUsage")

dependencyResolutionManagement {
    repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
    repositories {
        mavenLocal()
        mavenCentral()
    }
}

pluginManagement {
    repositories {
        mavenLocal()
        mavenCentral()
    }
}

include("app")
include("feature:foo")
include("feature:bar")
include("shared:foo")

includeBuild("plugin") {
    dependencySubstitution {
        substitute(module("org.haliksar:plugin")).using(project(":"))
    }
}