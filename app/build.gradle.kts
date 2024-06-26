plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.quickcashcsci3130g_11"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.quickcashcsci3130g11"
        minSdk = 33
        maxSdk = 34
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures{
        viewBinding = true
        dataBinding = true
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/DEPENDENCIES"
            excludes += "META-INF/gradle/incremental.annotation.processors"
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/LICENSE-notice.md"
        }
    }


    configurations {
        implementation {
            exclude(module = "protobuf-java")
        }
    }

    testOptions {
        // Used for Unit testing Android dependent elements in /test folder
        unitTests.isIncludeAndroidResources = true
        unitTests.isReturnDefaultValues = true
    }


    dependencies {

//    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")

        // Import the Firebase BoM
        implementation(platform("com.google.firebase:firebase-bom:32.3.1"))


        // TODO: Add the dependencies for Firebase products you want to use
        // When using the BoM, don't specify versions in Firebase dependencies
        implementation ("com.google.firebase:firebase-analytics")
        implementation("com.algolia:algoliasearch:4.0.0-beta.10")
        implementation("androidx.test.espresso:espresso-contrib:3.5.1"){
            exclude( group = "com.google.protobuf", module = "protobuf-lite")
        }

        implementation ("com.google.android.gms:play-services-maps:18.0.0")
        implementation ("com.google.android.gms:play-services-location:18.0.0")
        implementation("androidx.recyclerview:recyclerview:1.3.2")

        // Add the dependencies for any other desired Firebase products
        // https://firebase.google.com/docs/android/setup#available-libraries
        constraints {
            implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.8.22") {
                because("kotlin-stdlib-jdk7 is now a part of kotlin-stdlib")
            }
            implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.22") {
                because("kotlin-stdlib-jdk8 is now a part of kotlin-stdlib")
            }
        }
        androidTestImplementation("androidx.test.uiautomator:uiautomator:2.3.0-alpha03")
        implementation("com.google.android.material:material:1.9.0")
        implementation("androidx.constraintlayout:constraintlayout:2.2.0-alpha12")
        implementation(platform("com.google.firebase:firebase-bom:32.3.1"))
        implementation("com.google.firebase:firebase-analytics-ktx")
        implementation("com.google.firebase:firebase-auth-ktx")
        implementation("androidx.appcompat:appcompat:1.7.0-alpha03")
        implementation("com.google.firebase:firebase-database")
        implementation("com.google.android.gms:play-services-auth:20.7.0")
        implementation("com.google.gms:google-services:4.4.0")
        implementation("com.google.firebase:firebase-firestore:24.8.1")
        implementation ("com.google.auth:google-auth-library-oauth2-http:1.19.0")
        implementation("com.google.android.gms:play-services-location:21.0.1")
        implementation ("com.google.android.gms:play-services-maps:17.0.1")
        testImplementation("junit:junit:4.12")
        implementation ("com.paypal.sdk:paypal-android-sdk:2.14.2")


        /*
            testImplementation("androidx.test:runner:1.5.2")
        */
        /*
            testImplementation("androidx.arch.core:core-testing:2.2.0")
        */


        testImplementation("androidx.test:core:1.5.0")
        testImplementation("androidx.test:rules:1.5.0")
        testImplementation("androidx.test.ext:junit:1.1.5")
        androidTestImplementation("androidx.test.ext:junit:1.1.5")
        /*
            androidTestImplementation("junit:junit:4.13.2")
        */
        androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
        androidTestImplementation ("androidx.test.espresso:espresso-intents:3.5.1")

        testImplementation ("junit:junit:4.13.2")
        testImplementation ("org.mockito:mockito-core:5.7.0")

        androidTestImplementation ("androidx.test.ext:junit:1.1.3")
        androidTestImplementation ("androidx.test.espresso:espresso-core:3.4.0")

        androidTestImplementation ("org.mockito:mockito-core:3.12.4")
        implementation ("io.mockk:mockk:1.12.0")
        androidTestImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
        androidTestImplementation("org.junit.jupiter:junit-jupiter:5.8.1")

    }}
dependencies {
//    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
    implementation(files("../Library/MPAndroidChart-v3.0.1.jar"))
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
}