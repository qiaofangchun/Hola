pluginManagement {
    repositories {
        maven("https://maven.aliyun.com/repository/gradle-plugin")
        mavenCentral()
        gradlePluginPortal()
        google()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven("https://maven.aliyun.com/repository/public/")
        maven("https://maven.aliyun.com/repository/central")
        mavenCentral()
        google()
    }
}

rootProject.name = "Hola"
include(":app:weather")
include(":app:media")
include(":lib_arch")
include(":lib_common")
include(":lib_base")
include(":lib_blur")
include(":lib_extends")
include(":lib_network")
include(":lib_viewbind")
include(":lib_datastore")
include(":lib_location")