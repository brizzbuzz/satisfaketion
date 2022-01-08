plugins {
  id("io.bkbn.sourdough.library")
}

dependencies {
  implementation(libs.kotlin.reflect)
  testImplementation(libs.bundles.test)
  detektPlugins(libs.detekt.formatting)
}
