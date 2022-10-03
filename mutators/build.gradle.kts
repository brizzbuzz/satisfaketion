plugins {
  kotlin("multiplatform")
  kotlin("plugin.serialization") version "1.6.20"
  id("io.kotest.multiplatform") version "5.4.2"
  id("io.gitlab.arturbosch.detekt") version "1.21.0"
  id("com.adarshr.test-logger") version "3.2.0"
  id("org.jetbrains.dokka")
  id("maven-publish")
  id("java-library")
  id("signing")
}

//sourdough {
//  githubOrg.set("unredundant")
//  githubRepo.set("satisfaketion")
//  libraryName.set("Satisfaketion")
//  libraryDescription.set("A collection of useful Satisfaketion mutators")
//  licenseName.set("MIT License")
//  licenseUrl.set("https://mit-license.org")
//  developerId.set("unredundant")
//  developerName.set("Ryan Brink")
//  developerEmail.set("admin@bkbn.io")
//}

dependencies {
  detektPlugins(group = "io.gitlab.arturbosch.detekt", name = "detekt-formatting", version = "1.21.0")
}

kotlin {
  jvm {
    compilations.all {
      kotlinOptions.jvmTarget = "1.8"
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
        implementation("co.touchlab:kermit:1.1.0")
      }
    }
    val commonTest by getting {
      dependencies {
        implementation("io.kotest:kotest-assertions-core:5.4.2")
        implementation("io.kotest:kotest-framework-engine:5.4.2")
      }
    }
    val jvmMain by getting {
      dependencies {
        implementation("io.kotest:kotest-runner-junit5-jvm:5.4.2")
      }
    }
    val jvmTest by getting
    val jsMain by getting
    val jsTest by getting
//    val nativeMain by getting
//    val nativeTest by getting
  }
}
