import io.bkbn.sourdough.gradle.core.extension.SourdoughLibraryExtension

plugins {
  id("io.bkbn.sourdough.root") version "0.3.3"
  id("com.github.jakemarsden.git-hooks") version "0.0.2"
}

sourdough {
  toolChainJavaVersion.set(JavaLanguageVersion.of(JavaVersion.VERSION_17.majorVersion))
  jvmTarget.set(JavaVersion.VERSION_11.majorVersion)
  compilerArgs.set(listOf("-opt-in=kotlin.RequiresOptIn"))
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

subprojects {
    apply(plugin = "io.bkbn.sourdough.library")

    configure<SourdoughLibraryExtension> {
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
}
