plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.joehxblog.tictactoe"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.joehxblog.tictactoe"
        minSdk = 24
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.1")
    implementation("androidx.gridlayout:gridlayout:1.0.0")
    implementation("androidx.preference:preference:1.1.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.11.0-M2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.11.0-M2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.11.0-M2")
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}

tasks.withType<Test>  {
    useJUnitPlatform()
}