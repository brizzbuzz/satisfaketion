plugins {
  kotlin("jvm")
  id("io.bkbn.sourdough.library.jvm") version "0.5.6"
  id("io.gitlab.arturbosch.detekt") version "1.19.0"
  id("com.adarshr.test-logger") version "3.1.0"
  id("org.jetbrains.dokka")
  id("maven-publish")
  id("java-library")
  id("signing")
}

sourdough {
  githubOrg.set("unredundant")
  githubRepo.set("satisfaketion")
  libraryName.set("Satisfaketion")
  libraryDescription.set("A collection of useful Satisfaketion mutators")
  licenseName.set("MIT License")
  licenseUrl.set("https://mit-license.org")
  developerId.set("unredundant")
  developerName.set("Ryan Brink")
  developerEmail.set("admin@bkbn.io")
}

dependencies {
  api(projects.satisfaketionCore)
  detektPlugins(group = "io.gitlab.arturbosch.detekt", name = "detekt-formatting", version = "1.19.0")
}
repositories {
  mavenCentral()
}

testing {
  suites {
    named("test", JvmTestSuite::class) {
      useJUnitJupiter()
      dependencies {
        // Kotest
        implementation("io.kotest:kotest-runner-junit5-jvm:5.1.0")
        implementation("io.kotest:kotest-assertions-core-jvm:5.1.0")
      }
    }
  }
}
