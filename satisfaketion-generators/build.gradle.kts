plugins {
  `java-library`
  `maven-publish`
  signing
}

dependencies {
  api(projects.satisfaketionCore)
  implementation(libs.bundles.logging)
  testImplementation(libs.bundles.test)
  detektPlugins(libs.detekt.formatting)
}

publishing {
  publications {
    create<MavenPublication>("satisfaketion") {
      from(components["kotlin"])
      artifact(tasks.sourcesJar)
      artifact(tasks.javadocJar)

      groupId = project.group.toString()
      artifactId = project.name.toLowerCase()
      version = project.version.toString()

      pom {
        name.set("Satisfaketion")
        description.set("Highly extensible fake data generator")
        url.set("https://github.com/rgbrizzlehizzle/satisfaketion")
        licenses {
          license {
            name.set("MIT License")
            url.set("https://mit-license.org/")
          }
        }
        developers {
          developer {
            id.set("rgbrizzlehizzle")
            name.set("Ryan Brink")
            email.set("admin@bkbn.io")
          }
        }
        scm {
          connection.set("scm:git:git://github.com/rgbrizzlehizzle/knotion.git")
          developerConnection.set("scm:git:ssh://github.com/rgbrizzlehizzle/knotion.git")
          url.set("https://github.com/rgbrizzlehizzle/knotion.git")
        }
      }
    }
  }
}

signing {
  val signingKey: String? by project
  val signingPassword: String? by project
  useInMemoryPgpKeys(signingKey, signingPassword)
  sign(publishing.publications)
}
