import dev.shushant.localization.plugin.utils.Languages

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dev.shushant.localization.plugin") version "1.0.1"
}

android {
    namespace = "com.example.androidassist"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.androidassist"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

localization {
    supportedLang = listOf(
        // Add Supported Languages here
        Languages.English,
        Languages.Urdu,
        Languages.Arabic,
        Languages.Spanish,
        Languages.French,
        Languages.ChineseSimplified
    )
    pathToGenerateSupportedLanguageEnum = "${projectDir}/app/src/main/java/com/example/androidassist/sharedComponents/dataClasses"
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.recyclerview:recyclerview:1.4.0-alpha01")
    implementation("androidx.activity:activity:1.8.0")
    implementation("androidx.test:core-ktx:1.5.0")
    implementation("com.github.bumptech.glide:glide:4.14.2")
    implementation("com.jsibbold:zoomage:1.3.1")
    testImplementation("junit:junit:4.13.2")
    // Optional -- Robolectric environment
    testImplementation ("org.robolectric:robolectric:+")
    testImplementation("androidx.test:core:1.5.0")
    testImplementation("org.mockito:mockito-core:3.11.2")
    testImplementation("org.mockito.kotlin:mockito-kotlin:3.2.0")
    testImplementation("io.mockk:mockk:1.12.0")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("androidx.camera:camera-camera2:1.0.0-beta07")
    implementation("androidx.camera:camera-lifecycle:1.0.0-beta07")
    implementation("androidx.camera:camera-view:1.0.0-alpha14")
}