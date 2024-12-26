pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "edison_android_exercise"

include(":app")
include(":libs:lib_datastore")
include(":libs:lib_network")
includeBuild("build-src")

// this line is required, somehow the convention has some blocking process, even no test classes there
gradle.startParameter.excludedTaskNames.addAll(listOf(":build-src:testClasses"))
