# Satisfaketion

A Zero-Dependency Kotlin Faker implementation built to leave you fully satisfied 😏 ... With your fake data

[![version](https://img.shields.io/maven-central/v/io.github.unredundant/satisfaketion-core?style=flat-square)](https://search.maven.org/search?q=io.github.unredundant%20satisfaketion)

## How to Install 🚀

Satisfaketion publishes all releases to Maven Central.  As such, using the stable version of `Satisfaketion` is as simple 
as declaring it as an implementation dependency in your `build.gradle.kts`

```kotlin
repositories {
  mavenCentral()
}

dependencies {
  // other (less cool) dependencies
  testImplementation("io.github.unredundant:satisfaketion-core:latest.release")
  testImplementation("io.github.unredundant:satisfaketion-generators:latest.release")
  testImplementation("io.github.unredundant:satisfaketion-mutators:latest.release")
}
```

The last two dependencies are optional, as they are the out-of-the-box generators and mutators that Satisfaketion provides,
but they are by no means mandatory.  However, if you write an awesome generator or mutator that you think the community 
would love, please open an issue [here](https://github.com/unredundant/satisfaketion/issues) to discuss adding it 
to the repository

## Documentation

All documentation for the library is kept [here](https://unredundant.github.io/satisfaketion/index.html)
