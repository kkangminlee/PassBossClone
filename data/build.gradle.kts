plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    compileSdk = Constants.compileSdk

    defaultConfig {
        minSdk = Constants.minSdk
        targetSdk = Constants.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = Versions.javaVersion
        targetCompatibility = Versions.javaVersion
    }
    kotlinOptions {
        jvmTarget = Versions.jvmVersion
    }
}

dependencies {
    implementation(project(":domain"))

    AndroidXDependencies.run {
        implementation(hilt)
        implementation(security)
        implementation(coreKtx)
        implementation(pagingRuntime)
    }
    KotlinDependencies.run {
        implementation(kotlin)
        implementation(coroutines)
    }

    ThirdPartyDependencies.run {
        implementation(retrofit)
        implementation(retrofitGsonConverter)
        implementation(timber)
    }
    TestDependencies.run {
        testImplementation(jUnit)
        androidTestImplementation(androidTest)
        androidTestImplementation(espresso)
    }
}