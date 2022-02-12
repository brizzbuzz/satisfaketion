package io.github.unredundant.satisfaketion.core

import kotlin.random.Random
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.typeOf

actual class Faker<T : Any> {
  actual fun generate(seed: Random): T {
    TODO("Not yet implemented")
  }

  actual fun generate(): T = generate(Random.Default)
}
