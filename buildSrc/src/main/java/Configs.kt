object Configs {

    object Android {
        const val applicationId = "com.taurus.kotlincoroutineskata"
        const val minSdkVersion = 21
        const val targetSdkVersion = 28
        const val compileSdkVersion = 28
        const val versionCode = 1
        const val versionName = "1.0"
        const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    private const val supportVersion = "28.1.1"
    private const val constraintLayoutVersion = "1.1.3"
    private const val lifecycleExtensionVersion = "1.1.1"
    private const val coroutinesVersion = "1.0.1"

    object Libs {
        val appcompat = "com.android.support:appcompat-v7:$supportVersion"
        val constraintLayout = "com.android.support.constraint:constraint-layout:$constraintLayoutVersion"
        val lifeCycleExtension = "android.arch.lifecycle:extensions:$lifecycleExtensionVersion"
        val lifeCycleCompiler = "android.arch.lifecycle:compiler:$lifecycleExtensionVersion"
        val courotinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion"
        val courotinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion"
    }

    private const val jUnitVersion = "4.12"
    private const val androidTestRunnerVersion = "1.0.2"
    private const val androidEspressoVersion = "3.0.2"

    object TestLibs {
        val junit = "junit:junit:$jUnitVersion"
        val testRunner = "com.android.support.test:runner:$androidTestRunnerVersion"
        val espresso = "com.android.support.test.espresso:espresso-core:$androidEspressoVersion"
    }

}



