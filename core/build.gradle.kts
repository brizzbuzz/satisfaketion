plugins {
  kotlin("multiplatform")
  id("io.bkbn.sourdough.library.mpp")
  id("io.kotest.multiplatform")
  id("io.gitlab.arturbosch.detekt")
  id("com.adarshr.test-logger")
  id("maven-publish")
  id("java-library")
  id("signing")
}

sourdoughLibrary {
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
      dependencies {
        implementation(kotlin("stdlib"))
      }
    }
//    val commonTest by getting
    val jvmMain by getting {
      dependencies {
        implementation(kotlin("stdlib"))
        implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.10")
      }
    }
    val jvmTest by getting {
      dependencies {
        implementation("io.kotest:kotest-runner-junit5-jvm:5.7.1")
        implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.1")
        implementation("io.kotest:kotest-assertions-kotlinx-time-jvm:4.4.3")
      }
    }
    val jsMain by getting
    val jsTest by getting
//    val nativeMain by getting
//    val nativeTest by getting
  }
}
