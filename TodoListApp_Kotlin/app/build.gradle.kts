// apply plugin: 'com.android.application'
// apply plugin: 'kotlin-android'

// android {
//     compileSdkVersion 25
//     buildToolsVersion "25.0.2"
//     defaultConfig {
//         applicationId "io.viper.android.todolist"
//         minSdkVersion 19
//         targetSdkVersion 25
//         versionCode 1
//         versionName "1.0"
//         testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
//     }
//     buildTypes {
//         release {
//             minifyEnabled false
//             proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
//         }
//     }
//     sourceSets {
//         main.java.srcDirs += 'src/main/kotlin'
//     }
// }

// dependencies {
//     compile fileTree(dir: 'libs', include: ['*.jar'])
//     androidTestCompile('com.android.support.test.espresso:espresso-core:2.2.2', {
//         exclude group: 'com.android.support', module: 'support-annotations'
//     })
//     compile 'com.android.support:appcompat-v7:25.1.1'
//     compile 'com.android.support:design:25.1.1'
//     testCompile 'junit:junit:4.12'
//     compile "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"
// }
// repositories {
//     mavenCentral()
// }


plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "io.viper.android.todolist"
    compileSdk = 34

    defaultConfig {
        applicationId = "io.viper.android.todolist"
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
