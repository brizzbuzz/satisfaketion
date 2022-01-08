plugins {
  id("io.bkbn.sourdough.library")
}

dependencies {
  implementation(libs.kotlin.reflect)
  testImplementation(libs.bundles.test)
  testImplementation(group = "org.jetbrains.kotlinx", name = "kotlinx-datetime", version = "0.3.1")
  testImplementation("io.kotest:kotest-assertions-kotlinx-time-jvm:4.4.3")
  detektPlugins(libs.detekt.formatting)
}
