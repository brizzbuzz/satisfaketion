plugins {
  `java-library`
  `maven-publish`
}

dependencies {
  implementation(libs.kotlin.reflect)
  implementation(libs.bundles.logging)
  testImplementation(libs.bundles.test)
  detektPlugins(libs.detekt.formatting)
}

publishing {
  publications {
    create<MavenPublication>("satisfaketion") {
      from(components["kotlin"])
      artifact(tasks.sourcesJar)
    }
  }
}
