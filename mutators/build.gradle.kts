plugins {
  kotlin("multiplatform")
  kotlin("plugin.serialization")
  id("io.bkbn.sourdough.library.mpp")
  id("io.kotest.multiplatform")
  id("io.gitlab.arturbosch.detekt")
  id("com.adarshr.test-logger")
  id("maven-publish")
  id("java-library")
  id("signing")
}

// https://youtrack.jetbrains.com/issue/KT-46466
tasks.withType<AbstractPublishToMaven>().configureEach {
  val signingTasks = tasks.withType<Sign>()
  mustRunAfter(signingTasks)
}

sourdoughLibrary {
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
  detektPlugins(group = "io.gitlab.arturbosch.detekt", name = "detekt-formatting", version = "1.23.1")
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
  js {
    // browser()
    nodejs()
  }
  sourceSets {
    val commonMain by getting {
      resources.srcDirs("resources")
      dependencies {
        implementation(kotlin("stdlib"))
        implementation(projects.satisfaketionCore)
        implementation("co.touchlab:kermit:1.2.2")
      }
    }
    val commonTest by getting {
      dependencies {
        implementation("io.kotest:kotest-assertions-core:5.7.2")
        implementation("io.kotest:kotest-framework-engine:5.7.2")
      }
    }
    val jvmMain by getting {
      dependencies {
        implementation("io.kotest:kotest-runner-junit5-jvm:5.7.2")
      }
    }
    val jvmTest by getting
    val jsMain by getting
    val jsTest by getting
//    val nativeMain by getting
//    val nativeTest by getting
  }

  val publicationsFromMainHost =
    listOf(jvm(), js()).map { it.name } + "kotlinMultiplatform"
  publishing {
    publications {
      matching { it.name in publicationsFromMainHost }.all {
        val targetPublication = this@all
        tasks.withType<AbstractPublishToMaven>()
          .matching { it.publication == targetPublication }
          .configureEach { onlyIf { findProperty("isMainHost") == "true" } }
      }
    }
  }
}
