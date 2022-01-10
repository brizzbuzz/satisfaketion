package io.github.unredundant.satisfaketion.core

import kotlin.random.Random

interface Faker<T> {
  fun generate(seed: Random): T
}
