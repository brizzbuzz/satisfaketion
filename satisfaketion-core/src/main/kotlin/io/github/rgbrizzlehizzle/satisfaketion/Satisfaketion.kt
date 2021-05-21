package io.github.rgbrizzlehizzle.satisfaketion

import kotlin.random.Random
import kotlin.reflect.KClass

class Satisfaketion(
  var locale: String = "en-us", // TODO Enum?
  var random: Random = Random.Default,
  var fakes: Map<KClass<*>, Faker<*>> = emptyMap()
) {
  fun <T : Any> register(clazz: KClass<T>, faker: Faker<T>) {
    // TODO allow overrides as a separate method?
    require(!fakes.containsKey(clazz)) { "$clazz has already been registered" }
    fakes = fakes.plus(clazz to faker)
  }

  fun <T : Any> register(clazz: KClass<T>, init: Faker<T>.() -> Unit) {
    val builder = Faker<T>()
    builder.apply(init)
    register(clazz, builder)
  }
}

fun satisfaketion(block: Satisfaketion.() -> Unit) = Satisfaketion().apply(block)
