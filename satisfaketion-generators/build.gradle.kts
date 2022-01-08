plugins {
  id("io.bkbn.sourdough.library")
  kotlin("plugin.serialization") version "1.6.0"
}

dependencies {
  api(projects.satisfaketionCore)
  implementation(libs.kotlinx.serialization.json)
  implementation(libs.kaml)
  implementation(libs.bundles.logging)
  testImplementation(projects.satisfaketionMutators)
  testImplementation(libs.bundles.test)
  detektPlugins(libs.detekt.formatting)
}
