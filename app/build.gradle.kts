plugins {
	alias(libs.plugins.android.application)
	kotlin("kapt")
	alias(libs.plugins.kotlin.android)
	alias(libs.plugins.compose.compiler)
	alias(libs.plugins.kotlin.parcelize)
	alias(libs.plugins.hilt)
	alias(libs.plugins.navigation.safe.args)
	//alias(libs.plugins.ksp)
}

android {
	namespace = "pe.com.gianbravo.blockedcontacts"
	compileSdk = 35
	android.buildFeatures.buildConfig = true
	signingConfigs {
		create("release") {
			// You need to specify either an absolute path or include the
			// keystore file in the same directory as the build.gradle file.
			storeFile = file("giancappKey.jks")
			storePassword = "A1S2D6E123789gian"
			keyAlias = "key0gian"
			keyPassword = "102938gian102938"
		}
	}

	defaultConfig {
		applicationId = "pe.com.gianca.blockedcontacts"
		minSdk = 23
		targetSdk = 35
		versionCode = 1
		versionName = "1.0"

		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

		javaCompileOptions {
			annotationProcessorOptions {
				arguments["room.schemaLocation"] =
					"$projectDir/schemas"
			}
		}
	}

	buildTypes {
		getByName("release") {
			isMinifyEnabled = false
			//proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
			signingConfig = signingConfigs.getByName("release")
			isDebuggable = true
			applicationIdSuffix = ".prod"
			buildConfigField(
				"String",
				"SHARED_PREFERENCE_NAME",
				"\"SHARED_PREFERENCE_NAME\""
			)
		}

		getByName("debug") {
			resValue("string", "app_name", "BlacklistApp Alpha")
			buildConfigField(
				"String",
				"SHARED_PREFERENCE_NAME",
				"\"SHARED_PREFERENCE_NAME_DEBUG\""
			)
			applicationIdSuffix = ".alfa"
			versionNameSuffix = "-debug"
			isDebuggable = true
		}
	}
	compileOptions {
		isCoreLibraryDesugaringEnabled = true
		sourceCompatibility = JavaVersion.VERSION_11
		targetCompatibility = JavaVersion.VERSION_11
	}
	kotlinOptions {
		jvmTarget = "11"
	}
	buildFeatures {
		viewBinding = true
		compose = true
	}
	kapt {
		correctErrorTypes = true
	}
	configurations.implementation {
		exclude(group = "com.intellij", module = "annotations")
	}
}

dependencies {

	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.appcompat)
	implementation(libs.material)
	implementation(libs.androidx.constraintlayout)
	implementation(libs.androidx.navigation.fragment.ktx)
	implementation(libs.androidx.navigation.ui.ktx)
	implementation(libs.work.runtime.ktx)

	// Splash Screen
	implementation(libs.androidx.core.splashscreen)

	// Accompanist
	implementation (libs.accompanist.permissions)


	// Hilt - Migrate to ksp when it's stable
	implementation(libs.hilt.android)
	kapt(libs.hilt.android.compiler)
	// Hilt extensions
	implementation(libs.hilt.navigation.fragment)
	implementation(libs.hilt.navigation.compose)
	implementation(libs.hilt.work)
	kapt(libs.hilt.compiler)

	//Room
	implementation(libs.room.ktx)
	implementation(libs.room.runtime)
	kapt(libs.room.compiler)

	// Desugar for java 8
	coreLibraryDesugaring(libs.desugar)

	// Gson
	implementation (libs.gson)

	implementation(libs.kotlin.reflect)


	val composeBom = platform("androidx.compose:compose-bom:2025.02.00")
	implementation(composeBom)
	androidTestImplementation(composeBom)
	implementation(libs.androidx.fragment.compose)

	// Choose one of the following:
	// Material Design 3
	implementation(libs.androidx.compose.material3)

	// Android Studio Preview support
	implementation(libs.androidx.compose.ui.tooling.preview)
	implementation(libs.androidx.compose.ui.tooling)

	// UI Tests
	androidTestImplementation(libs.androidx.ui.test.junit4)
	debugImplementation(libs.androidx.ui.test.manifest)


	// Optional - Integration with activities
	implementation(libs.androidx.activity.compose)
	// Optional - Integration with ViewModels
	implementation(libs.androidx.lifecycle.viewmodel.compose)
	// Optional - Integration with LiveData
	implementation(libs.androidx.runtime.livedata)

	implementation(libs.timber)
	implementation(libs.firebase.crashlytics)

	//Koin
	implementation(libs.koin.core)
	implementation(libs.koin.android)

	implementation("io.reactivex.rxjava2:rxandroid:2.0.2")


	testImplementation(libs.junit)
	androidTestImplementation(libs.androidx.junit)
	androidTestImplementation(libs.androidx.espresso.core)
}