object Configs {

    private const val supportVersion = "28.1.1"
    private const val constraintLayoutVersion = "1.1.3"

    object Android {
        const val applicationId = "com.taurus.kotlincoroutineskata"
        const val minSdkVersion = 21
        const val targetSdkVersion = 28
        const val compileSdkVersion = 28
        const val versionCode = 1
        const val versionName = "1.0"
        const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    object Libs {
        val appcompat = "com.android.support:appcompat-v7:$supportVersion"
        val constraintLayout = "com.android.support.constraint:constraint-layout:$constraintLayoutVersion"
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



