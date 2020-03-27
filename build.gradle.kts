import org.gradle.jvm.tasks.Jar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jmailen.gradle.kotlinter.tasks.LintTask

buildscript {
    val kotlinVersion: String by project

    repositories {
        google()
        jcenter()
        mavenCentral()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("org.jmailen.gradle:kotlinter-gradle:2.2.0")
    }
}


plugins {
    base
    `java-library`
    kotlin("jvm") version "1.3.70" apply false
    id("org.nosphere.gradle.github.actions") version "1.1.0"
}

allprojects {
    apply(plugin = "base")

    repositories {
        google()
        jcenter()
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "kotlin")

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "13"
        kotlinOptions.freeCompilerArgs = listOf(
            "-Xjsr305=strict",
            "-XXLanguage:+InlineClasses",
            "-Xopt-in=kotlin.RequiresOptIn"
        )
        kotlinOptions.allWarningsAsErrors = true
    }

    @Suppress("UnstableApiUsage")
    tasks.withType<Jar> {
        archiveBaseName.set("${this@subprojects.parent?.name}-${this@subprojects.name}")
    }

    @Suppress("UnstableApiUsage")
    tasks.register<Jar>("sourcesJar") {
        from(sourceSets.main.get().allSource)
        archiveClassifier.set("sources")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    tasks.withType<LintTask> {
        dependsOn("formatKotlin")
    }
}