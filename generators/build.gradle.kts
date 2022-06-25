plugins {
  kotlin("multiplatform")
  kotlin("plugin.serialization") version "1.6.21"
  id("io.kotest.multiplatform") version "5.3.1"
  id("io.bkbn.sourdough.library.mpp") version "0.8.0"
  id("io.gitlab.arturbosch.detekt") version "1.20.0"
  id("com.adarshr.test-logger") version "3.2.0"
  id("org.jetbrains.dokka")
  id("maven-publish")
  id("java-library")
  id("signing")
}

sourdough {
  githubOrg.set("unredundant")
  githubRepo.set("satisfaketion")
  libraryName.set("Satisfaketion")
  libraryDescription.set("A collection of useful Satisfaketion generators")
  licenseName.set("MIT License")
  licenseUrl.set("https://mit-license.org")
  developerId.set("unredundant")
  developerName.set("Ryan Brink")
  developerEmail.set("admin@bkbn.io")
}

dependencies {
  detektPlugins(group = "io.gitlab.arturbosch.detekt", name = "detekt-formatting", version = "1.20.0")
}

kotlin {
  sourceSets {
    val commonMain by getting {
      resources.srcDirs("resources")
      dependencies {
        implementation(kotlin("stdlib"))
        implementation(projects.satisfaketionCore)
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")
        implementation("com.squareup.okio:okio:3.1.0")
        implementation("co.touchlab:kermit:1.1.3")
      }
    }
    val commonTest by getting {
      dependencies {
        implementation("io.kotest:kotest-assertions-core:5.3.2")
        implementation("io.kotest:kotest-framework-engine:5.3.2")
      }
    }
    val jvmMain by getting {
      resources.srcDirs("resources")
      dependencies {
        implementation("io.kotest:kotest-runner-junit5-jvm:5.3.2")
      }
    }
    val jvmTest by getting
    val jsMain by getting {
      resources.srcDirs("resources")
      dependencies {
        implementation("com.squareup.okio:okio-nodefilesystem:3.1.0")
      }
    }
    val jsTest by getting
    val nativeMain by getting
    val nativeTest by getting {
      resources.srcDirs("resources")
    }
  }
}
