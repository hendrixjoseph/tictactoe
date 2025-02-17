plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.joehxblog.tictactoe"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.joehxblog.tictactoe"
        minSdk = 24
        targetSdk = 35
        versionCode = 3
        versionName = "1.24.35"

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