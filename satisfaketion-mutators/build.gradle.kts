plugins {
  id("io.bkbn.sourdough.library")
}

dependencies {
  api(projects.satisfaketionCore)
  implementation(libs.bundles.logging)
  testImplementation(libs.bundles.test)
  detektPlugins(libs.detekt.formatting)
}
