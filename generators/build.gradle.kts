plugins {
  kotlin("multiplatform")
  kotlin("plugin.serialization") version "1.7.20"
  id("io.bkbn.sourdough.library.mpp") version "0.12.0"
  id("io.kotest.multiplatform") version "5.5.1"
  id("io.gitlab.arturbosch.detekt") version "1.21.0"
  id("com.adarshr.test-logger") version "3.2.0"
  id("maven-publish")
  id("java-library")
  id("signing")
}

sourdoughLibrary {
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
  detektPlugins(group = "io.gitlab.arturbosch.detekt", name = "detekt-formatting", version = "1.21.0")
}

kotlin {
  jvm {
    compilations.all {
      kotlinOptions.jvmTarget = "17"
    }
    withJava()
    testRuns["test"].executionTask.configure {
      useJUnitPlatform()
    }
  }
  js(BOTH) {
    browser {
      commonWebpackConfig {
        cssSupport.enabled = true
      }
    }
  }
  sourceSets {
    val commonMain by getting {
      resources.srcDirs("resources")
      dependencies {
        implementation(kotlin("stdlib"))
        implementation(projects.satisfaketionCore)
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.0")
        implementation("com.squareup.okio:okio:3.2.0")
        implementation("co.touchlab:kermit:1.1.3")
      }
    }
    val commonTest by getting {
      dependencies {
        implementation("io.kotest:kotest-assertions-core:5.5.0")
        implementation("io.kotest:kotest-framework-engine:5.5.0")
      }
    }
    val jvmMain by getting {
      resources.srcDirs("resources")
      dependencies {
        implementation("io.kotest:kotest-runner-junit5-jvm:5.5.0")
      }
    }
    val jvmTest by getting
    val jsMain by getting {
      resources.srcDirs("resources")
      dependencies {
        implementation("com.squareup.okio:okio-nodefilesystem:3.2.0")
      }
    }
    val jsTest by getting
//    val nativeMain by getting
//    val nativeTest by getting {
//      resources.srcDirs("resources")
//    }
  }
}
