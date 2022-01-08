package io.github.rgbrizzlehizzle.satisfaketion.core

import kotlin.random.Random
import kotlin.reflect.KClass

class Satisfaketion(
  var random: Random = Random.Default,
  var fakes: Map<KClass<*>, Faker<*>> = emptyMap()
) {
  fun <T : Any> register(clazz: KClass<T>, faker: Faker<T>) {
    require(!fakes.containsKey(clazz)) { "$clazz has already been registered" }
    fakes = fakes.plus(clazz to faker)
  }

  fun <T : Any> register(clazz: KClass<T>, init: Faker<T>.() -> Unit) {
    val builder = Faker(clazz)
    builder.apply(init)
    register(clazz, builder)
  }

  inline fun <reified T : Any> generate(random: Random = Random.Default): T {
    val faker = fakes[T::class] ?: error("No registered faker for ${T::class}")
    return faker.generate(random) as T
  }
}

fun satisfaketion(block: Satisfaketion.() -> Unit) = Satisfaketion().apply(block)
