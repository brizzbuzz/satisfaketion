package io.github.unredundant.satisfaketion.core

import kotlin.random.Random

expect class Faker<T : Any> {
  fun generate(): T
  fun generate(seed: Random): T
}
