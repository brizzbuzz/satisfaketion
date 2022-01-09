plugins {
  id("io.bkbn.sourdough.library")
  kotlin("plugin.serialization") version "1.6.10"
}

dependencies {
  api(projects.satisfaketionCore)

  implementation(group = "org.jetbrains.kotlinx", name = "kotlinx-serialization-json", version = "1.3.2")
  implementation(group = "com.charleskorn.kaml", name = "kaml", version = "0.39.0")
  detektPlugins(group = "io.gitlab.arturbosch.detekt", name = "detekt-formatting", version = "1.19.0")
}

testing {
  suites {
    val test by getting(JvmTestSuite::class) {
      useJUnitJupiter()
      dependencies {
        // Satisfaketion
        implementation(projects.satisfaketionMutators)

        // Kotest
        implementation("io.kotest:kotest-runner-junit5-jvm:5.0.3")
        implementation("io.kotest:kotest-assertions-core-jvm:5.0.3")
      }
    }
  }
}
