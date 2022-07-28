plugins {
  kotlin("multiplatform")
  id("io.bkbn.sourdough.library.mpp") version "0.9.0"
  id("io.kotest.multiplatform") version "5.4.1"
  id("io.gitlab.arturbosch.detekt") version "1.21.0"
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
  libraryDescription.set("A data generator that is as beautiful and powerful as you are ❤️")
  licenseName.set("MIT License")
  licenseUrl.set("https://mit-license.org")
  developerId.set("unredundant")
  developerName.set("Ryan Brink")
  developerEmail.set("admin@bkbn.io")
}

dependencies {
  detektPlugins(group = "io.gitlab.arturbosch.detekt", name = "detekt-formatting", version = "1.21.0")
}

kotlin {
  sourceSets {
    val commonMain by getting {
      dependencies {
        implementation(kotlin("stdlib"))
      }
    }
    val commonTest by getting
    val jvmMain by getting {
      dependencies {
        implementation(kotlin("stdlib"))
        implementation("org.jetbrains.kotlin:kotlin-reflect:1.7.10")
      }
    }
    val jvmTest by getting {
      dependencies {
        implementation("io.kotest:kotest-runner-junit5-jvm:5.4.0")
        implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
        implementation("io.kotest:kotest-assertions-kotlinx-time-jvm:4.4.3")
      }
    }
    val jsMain by getting
    val jsTest by getting
    val nativeMain by getting
    val nativeTest by getting
  }
}
