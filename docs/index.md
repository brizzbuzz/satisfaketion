# Satisfaketion

Satisfaketion is a library for generating beautiful fake data in Kotlin.

Satisfaketion is broken into three main library modules

- Core
- Generators
- Mutators

# Core

The engine that powers Satisfaketion. Instantiating a satisfaketion object is as easy as declaring the helper function.

Once a Faker has been instantiated, type-safe generators can be associated with each member of the class.

## Faker

```kotlin
val satisfaketion = Faker<SimpleDataClass> {
  SimpleDataClass::a { TestPhoneGenerator }
  SimpleDataClass::b { SmolIntGenerator }
}

val example = faker.generate()
```

## Generator

A `Generator` is a functional interface that declares a single method `generate`

```kotlin
fun interface Generator<R> {
  fun generate(seed: Random): R
}
```

An example generator for a naive phone number could be

```kotlin
object TestPhoneGenerator : Generator<String> {
  override fun generate(seed: Random = Random.Default): String {
    val first = seed.nextInt(100..999)
    val second = seed.nextInt(100..999)
    val third = seed.nextInt(1000..9999)
    return "$first-$second-$third"
  }
}
```

## Mutator

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

using the existing `EnglishName` generators (from the generator module), you can declare a satisfaketion instance, with
mutators to add weighted mutability to the `prefix` and `suffix` fields

```kotlin
val satisfaketion = satisfaketion {
  register(MyPerson::class) {
    MyPerson::firstName { EnglishName.firstName }
    MyPerson::lastName { EnglishName.lastName }
    MyPerson::prefix { EnglishName.prefix.mutate(WeightedNullabilityMutator(0.25, seed)) }
    MyPerson::suffix { EnglishName.suffix.mutate(WeightedNullabilityMutator(0.25, seed)) }
  }
}
```

This would cause approximately 25% of generated objects to have a null field for `prefix` and/or `suffix`
