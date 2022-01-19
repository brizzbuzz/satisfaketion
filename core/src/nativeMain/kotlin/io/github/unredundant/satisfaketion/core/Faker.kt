package io.github.unredundant.satisfaketion.core

import kotlin.random.Random

actual class Faker<T : Any> {
  actual fun generate(seed: Random): T {
    TODO("Not yet implemented")
  }

  actual fun generate(): T = generate(Random.Default)
}
