# Satisfaketion

A Zero-Dependency Kotlin Faker implementation built to leave you fully satisfied 😏

... With your fake data

## How to Install

Satisfaketion uses GitHub packages as its repository.  Installing with Gradle is pretty painless.  In your `build.gradle.kts`
add the following

```kotlin
// 1 Setup a helper function to import any Github Repository Package
// This step is optional but I have a bunch of stuff stored on github so I find it useful 😄
fun RepositoryHandler.github(packageUrl: String) = maven { 
  name = "GithubPackages"
  url = uri(packageUrl)
  credentials { // TODO Not sure this is necessary for public repositories?
    username = java.lang.System.getenv("GITHUB_USER")
    password = java.lang.System.getenv("GITHUB_TOKEN")
  } 
}

// 2 Add the repo in question (in this case Kompendium)
repositories {
  github("https://maven.pkg.github.com/rgbrizzlehizzle/satisfaketion")
}

// 3 Add the package like any normal dependency
dependencies { 
  implementation("io.bkbn:satisfaketion-core:0.1.0-SNAPSHOT")
  implementation("io.bkbn:satisfaketion-core:0.1.0-SNAPSHOT")
}

```

## In Depth

Satisfaketion is broken into two main library modules

- Core
- Generators

### Core

The engine that powers Satisfaketion.  Instantiating a satisfaketion object is as easy as declaring the helper function.

Once a class has been registered, type-safe generators can be associated with each member of the class.

```kotlin
val satisfaketion = satisfaketion {
  register(SimpleDataClass::class) {
    SimpleDataClass::a { TestPhoneGenerator }
    SimpleDataClass::b { SmolIntGenerator }
  }
}

val example = satisfaketion.generate<SimpleDataClass>()
```

A `Generator` is a functional interface that declares a single method `generate`

```kotlin
fun interface Generator<R> {
  fun generate(): R
}
```

An example generator for a naive phone number could be 

```kotlin
object TestPhoneGenerator : Generator<String> {
  override fun generate(): String {
    val first = Random.Default.nextInt(100..999)
    val second = Random.Default.nextInt(100..999)
    val third = Random.Default.nextInt(1000..9999)
    return "$first-$second-$third"
  }
}
```

### Generators

Collection of useful generators to create fantastic fake data

TODO (doesn't exist yet)
