pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }

//    resolutionStrategy {
//        eachPlugin {
//            if( requested.id.id == "dagger.hilt.android.plugin") {
//                useModule("com.google.dagger:hilt-android-gradle-plugin:2.43.2")
//            }
//        }
//    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

    }
}

rootProject.name = "ComicsLibrary"
include(":app")
include(":Test")
