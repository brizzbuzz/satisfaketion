plugins {
  kotlin("jvm") version "1.7.20" apply false
  kotlin("multiplatform") version "1.7.20" apply false
  id("io.bkbn.sourdough.root") version "0.12.0"
  id("com.github.jakemarsden.git-hooks") version "0.0.2"
  id("org.jetbrains.dokka") version "1.6.20"
  id("org.jetbrains.kotlinx.kover") version "0.6.0"
  id("io.github.gradle-nexus.publish-plugin") version "1.1.0"
}

gitHooks {
  setHooks(
    mapOf(
      "pre-commit" to "detekt",
      "pre-push" to "test"
    )
  )
}

allprojects {
  group = "io.github.unredundant"
  version = run {
    val baseVersion =
      project.findProperty("project.version") ?: error("project.version needs to be set in gradle.properties")
    when ((project.findProperty("release") as? String)?.toBoolean()) {
      true -> baseVersion
      else -> "$baseVersion-SNAPSHOT"
    }
  }
}
