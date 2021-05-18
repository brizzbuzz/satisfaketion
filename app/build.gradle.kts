plugins {
  application
}

dependencies {
  implementation(projects.lib)
  implementation(libs.bundles.logging)
  testImplementation(libs.bundles.test)
  detektPlugins(libs.detekt.formatting)
}

application {
  mainClass.set("io.bkbn.sourdough.app.AppKt")
}
