package io.github.unredundant.satisfaketion.core.util

import io.github.unredundant.satisfaketion.core.Generator
import kotlin.random.Random
import kotlin.random.nextInt

object TestPhoneGenerator : Generator<String> {
  override fun generate(seed: Random): String {
    val first = seed.nextInt(100..999)
    val second = seed.nextInt(100..999)
    val third = seed.nextInt(1000..9999)
    return "$first-$second-$third"
  }
}

object SmolIntGenerator : Generator<Int> {
  override fun generate(seed: Random): Int = seed.nextInt(1..25)
}
