plugins {
    id("common-library")
}

android {
    defaultConfig {
        minSdk = 21
        compileSdk = 30
        targetSdk = 30

        testInstrumentationRunner = "com.karumi.shot.ShotTestRunner"
        testApplicationId = "com.vanpra.composematerialdialogs.color.test"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                    getDefaultProguardFile("proguard-android.txt"),
                    "proguard-rules.pro"
            )
        }
    }

    buildFeatures.compose = true

    packagingOptions.excludes.addAll(
        listOf(
            "META-INF/DEPENDENCIES.txt",
            "META-INF/LICENSE",
            "META-INF/LICENSE.txt",
            "META-INF/NOTICE",
            "META-INF/NOTICE.txt"
        )
    )

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Dependencies.AndroidX.Compose.version
    }
}

dependencies {
    api(project(":core"))
}

shot {
    tolerance = 1.0 // Tolerance needed for CI
}

val mavenCentralRepositoryUsername: String? by project
val mavenCentralRepositoryPassword: String? by project

publishing {
    repositories {
        withType<MavenArtifactRepository> {
            if (name == "local") {
                return@withType
            }

            val releasesRepoUrl = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            val snapshotsRepoUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")

            url = if((version as String).endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl

            credentials {
                username = mavenCentralRepositoryUsername
                password = mavenCentralRepositoryPassword
            }
        }
    }
}

mavenPublishing {
    publishToMavenCentral(com.vanniktech.maven.publish.SonatypeHost.S01)
}
