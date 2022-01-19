rootProject.name = "satisfaketion"
include("core")
include("generators")
include("mutators")

run {
  rootProject.children.forEach { it.name = "${rootProject.name}-${it.name}" }
}

// Feature Previews
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
  repositories {
    gradlePluginPortal()
    mavenLocal()
  }
}
