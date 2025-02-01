import com.android.utils.TraceUtils.simpleId

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    // KSP (어노테이션 읽기용)
    id("com.google.devtools.ksp") version "1.9.0-1.0.13" apply false
    // Hilt
    id("com.google.dagger.hilt.android") version libs.versions.hiltVersion apply false
}