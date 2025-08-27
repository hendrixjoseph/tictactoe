plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.joehxblog.tictactoe"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.joehxblog.tictactoe"
        minSdk = 24
        targetSdk = 36
        versionCode = 3
        versionName = "1.24.36"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.gridlayout)
    implementation(libs.preference)
    implementation(libs.sdp.android)
    implementation(libs.ssp.android)
    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testImplementation(libs.junit.jupiter.params)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}

tasks.withType<Test>  {
    useJUnitPlatform()
}