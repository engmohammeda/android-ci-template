plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

// ────────────────────────────────────────────────────────────────
// التوقيع: إن وُجدت متغيرات البيئة RELEASE_STORE_FILE (يضبطها خط
// الأتمتة من السرّ KEYSTORE_BASE64) نستخدم مفتاح الإنتاج، وإلا
// نستخدم debug.keystore في جذر المستودع إن وُجد (ينشئه الـ CI).
// ────────────────────────────────────────────────────────────────
val releaseStoreFile: String? = System.getenv("RELEASE_STORE_FILE")
val fallbackDebugKeystore = rootProject.file("debug.keystore")

android {
    namespace = "com.albukhaiti.template"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.albukhaiti.template"
        minSdk = 24
        targetSdk = 36
        versionCode = (System.getenv("GITHUB_RUN_NUMBER") ?: "1").toInt()
        versionName = "0.1.0"
    }

    signingConfigs {
        create("release") {
            when {
                releaseStoreFile != null -> {
                    storeFile = file(releaseStoreFile)
                    storePassword = System.getenv("RELEASE_STORE_PASSWORD") ?: "android"
                    keyAlias = System.getenv("RELEASE_KEY_ALIAS") ?: "androiddebugkey"
                    keyPassword = System.getenv("RELEASE_KEY_PASSWORD") ?: "android"
                }
                fallbackDebugKeystore.exists() -> {
                    storeFile = fallbackDebugKeystore
                    storePassword = "android"
                    keyAlias = "androiddebugkey"
                    keyPassword = "android"
                }
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig =
                if (releaseStoreFile != null || fallbackDebugKeystore.exists())
                    signingConfigs.getByName("release")
                else
                    signingConfigs.getByName("debug")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    testImplementation("junit:junit:4.13.2")
}
