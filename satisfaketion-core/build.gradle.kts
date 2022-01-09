plugins {
  id("io.bkbn.sourdough.library")
}

dependencies {
  implementation(group = "org.jetbrains.kotlin", name = "kotlin-reflect", version = "1.6.10")
  detektPlugins(group = "io.gitlab.arturbosch.detekt", name = "detekt-formatting", version = "1.19.0")
}

testing {
  suites {
    val test by getting(JvmTestSuite::class) {
      useJUnitJupiter()
      dependencies {
        // Kotest
        implementation("io.kotest:kotest-runner-junit5-jvm:5.0.3")
        implementation("io.kotest:kotest-assertions-core-jvm:5.0.3")
        implementation("io.kotest:kotest-assertions-kotlinx-time-jvm:4.4.3")

        // Date/Time
        implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.3.1")
      }
    }
  }
}
