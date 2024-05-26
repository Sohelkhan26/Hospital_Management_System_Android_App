plugins {
    id("com.android.application")
    id("com.google.gms.google-services")

}

android {
    namespace = "com.example.se_3120_project"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.se_3120_project"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }

}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-database:20.3.1")
    implementation("androidx.navigation:navigation-fragment:2.5.3")
    implementation("androidx.navigation:navigation-ui:2.5.3")
    implementation("androidx.test.espresso:espresso-intents:3.5.1")

    testImplementation ("org.mockito:mockito-core:3.11.2")
    androidTestImplementation("org.mockito:mockito-core:3.11.2")
    testImplementation("junit:junit:4.13.2")
    testImplementation("com.google.truth:truth:1.4.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation ("com.google.firebase:firebase-database:20.3.1")


//    // Core library
//    androidTestImplementation("androidx.test:core:$androidXTestVersion")
//
//    // AndroidJUnitRunner and JUnit Rules
//    androidTestImplementation("androidx.test:runner:$testRunnerVersion")
//    androidTestImplementation("androidx.test:rules:$testRulesVersion")
//
//    // Assertions
//    androidTestImplementation("androidx.test.ext:junit:$testJunitVersion")
//    androidTestImplementation("androidx.test.ext:truth:$truthVersion")
//
//    // Espresso dependencies
//    androidTestImplementation( "androidx.test.espresso:espresso-core:3.5.1")
//    androidTestImplementation( "androidx.test.espresso:espresso-contrib:3.5.1")
//    androidTestImplementation( "androidx.test.espresso:espresso-intents:3.5.1")
//    androidTestImplementation( "androidx.test.espresso:espresso-accessibility:3.5.1")
//    androidTestImplementation( "androidx.test.espresso:espresso-web:3.5.1")
//    androidTestImplementation( "androidx.test.espresso.idling:idling-concurrent:3.5.1")
//
//    // The following Espresso dependency can be either "implementation",
//    // or "androidTestImplementation", depending on whether you want the
//    // dependency to appear on your APK"s compile classpath or the test APK
//    // classpath.
//    androidTestImplementation( "androidx.test.espresso:espresso-idling-resource:$espressoVersion")
//

}