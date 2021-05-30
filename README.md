# Satisfaketion

A Zero-Dependency Kotlin Faker implementation built to leave you fully satisfied 😏 ... With your fake data

[![codecov](https://codecov.io/gh/rgbrizzlehizzle/satisfaketion/branch/main/graph/badge.svg?token=WG42G5BPLQ)](https://codecov.io/gh/rgbrizzlehizzle/satisfaketion)
[![version](https://img.shields.io/maven-central/v/io.github.rgbrizzlehizzle/satisfaketion-core?style=flat-square)](https://search.maven.org/artifact/io.github.rgbrizzlehizzle/satisfaketion-core/0.3.0/jar)

## How to Install 🚀

Satisfaketion publishes all releases to Maven Central.  As such, using the stable version of `Satisfaketion` is as simple 
as declaring it as an implementation dependency in your `build.gradle.kts`

```kotlin
repositories {
  mavenCentral()
}

dependencies {
  // other (less cool) dependencies
  testImplementation("io.github.rgbrizzlehizzle:satisfaketion-core:latest")
  testImplementation("io.github.rgbrizzlehizzle:satisfaketion-generators:latest")
  testImplementation("io.github.rgbrizzlehizzle:satisfaketion-mutators:latest")
}
```

The last two dependencies are optional, as they are the out-of-the-box generators and mutators that Satisfaketion provides,
but they are by no means mandatory.  However, if you write an awesome generator or mutator that you think the community 
would love, please open an issue [here](https://github.com/rgbrizzlehizzle/satisfaketion/issues) to discuss adding it 
to the repository

If you want to get a little spicy 🤠 every merge of Satisfaketion is published to the GitHub package registry.  Pulling 
from GitHub is slightly more involved, but such is the price you pay for bleeding edge fake data generation.  

```kotlin
// 1 Setup a helper function to import any Github Repository Package
// This step is optional but I have a bunch of stuff stored on github so I find it useful 😄
fun RepositoryHandler.github(packageUrl: String) = maven { 
  name = "GithubPackages"
  url = uri(packageUrl)
  credentials {
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
  implementation("io.bkbn:satisfaketion-core:latest")
}

```

## In Depth 👀

Satisfaketion is broken into three main library modules

- Core
- Generators
- Mutators

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

Another concept that is at the core of `Satisfaketion` is the `Mutator`.

A `Mutator` is another functional interface

```kotlin
fun interface Mutator<R, RR> {
  fun mutate(generator: Generator<R>): Generator<RR>
}
```

Mutators allow you to take an existing `Generator` and mutate it, allowing for expansive reuse of base generators.

Let's say you have a data class `MyPerson`

```kotlin
data class MyPerson(
    val firstName: String,
    val lastName: String,
    val prefix: String?,
    val suffix: String?,
)
```

using the existing `EnglishName` generator, you can declare a satisfaketion instance, with mutators to add weighted mutability to the `prefix` and `suffix` fields 

```kotlin
val satisfaketion = satisfaketion {
  register(MyPerson::class) {
    MyPerson::firstName { nameGenerator.firstName }
    MyPerson::lastName { nameGenerator.lastName }
    MyPerson::prefix { nameGenerator.prefix.mutate(WeightedNullabilityMutator(0.25, seed)) }
    MyPerson::suffix { nameGenerator.suffix.mutate(WeightedNullabilityMutator(0.25, seed)) }
  }
}
```

This would cause approximately 25% of generated objects to have a null field for `prefix` and/or `suffix`

### Generators ♺

Collection of useful generators to create fantastic fake data

The current list of pre-existing generators is

- English Names
- United States Address
- Beer
- Barcodes

If you would like to add a generator, please first open an issue [here](https://github.com/rgbrizzlehizzle/satisfaketion/issues) explaining the use case.

### Mutators 🦋

Collection of useful mutators

- `WeightedNullabilityMutator`: Given a weight between 0 and 1, will mutate a generator to provided interspersed null values in accordance with the provided weight
- `CollectionMutator`: Takes a standard generator and converts it to a `List` generator

## Limitations 🚨

Due to the reflective operations that satisfaketion performs, it will not work on non-public data classes. 

With that said, for internal classes, individual generators can still be used and applied directly to members
