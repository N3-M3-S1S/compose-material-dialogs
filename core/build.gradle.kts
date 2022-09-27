plugins {
    id("common-library")
}

android {
    defaultConfig {
        minSdk = 21
        compileSdk = 33
        targetSdk = 33

        testInstrumentationRunner = "com.karumi.shot.ShotTestRunner"
        testApplicationId = "com.vanpra.composematerialdialogs.test"
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

    packagingOptions.excludes.addAll(
        listOf(
            "META-INF/DEPENDENCIES.txt",
            "META-INF/LICENSE",
            "META-INF/LICENSE.txt",
            "META-INF/NOTICE",
            "META-INF/NOTICE.txt",
            "META-INF/AL2.0",
            "META-INF/LGPL2.1"
        )
    )

    buildFeatures {
        buildConfig = false
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Dependencies.AndroidX.Compose.compilerVersion
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
}

shot {
    tolerance = 1.0 // Tolerance needed for CI
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "com.nemesis.compose-material-dialogs"
            artifactId = "core"
            version = "1.0"

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}