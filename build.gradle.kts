plugins {
  kotlin("jvm") version "1.9.0" apply false
  kotlin("multiplatform") version "1.9.0" apply false
  kotlin("plugin.serialization") version "1.8.22" apply false
  id("io.bkbn.sourdough.library.mpp") version "0.12.0" apply false
  id("io.kotest.multiplatform") version "5.6.2" apply false
  id("io.gitlab.arturbosch.detekt") version "1.23.0" apply false
  id("com.adarshr.test-logger") version "3.2.0" apply false
  id("io.bkbn.sourdough.root") version "0.12.0"
  id("com.github.jakemarsden.git-hooks") version "0.0.2"
  id("io.github.gradle-nexus.publish-plugin") version "1.3.0"
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
