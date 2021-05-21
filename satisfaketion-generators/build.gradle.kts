plugins {
  `java-library`
  `maven-publish`
}

dependencies {
  implementation(projects.satisfaketionCore)
  implementation(libs.bundles.logging)
  testImplementation(libs.bundles.test)
  detektPlugins(libs.detekt.formatting)
}

publishing {
  publications {
    create<MavenPublication>("satisfaketion-generator") {
      from(components["kotlin"])
      artifact(tasks.sourcesJar)
    }
  }
}
