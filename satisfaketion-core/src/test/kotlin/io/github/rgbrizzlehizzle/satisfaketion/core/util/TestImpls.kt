package io.github.rgbrizzlehizzle.satisfaketion.core.util

import io.github.rgbrizzlehizzle.satisfaketion.core.Generator
import kotlin.random.Random
import kotlin.random.nextInt

object TestPhoneGenerator : Generator<String> {
  override fun generate(): String {
    val first = Random.Default.nextInt(100..999)
    val second = Random.Default.nextInt(100..999)
    val third = Random.Default.nextInt(1000..9999)
    return "$first-$second-$third"
  }
}

object SmolIntGenerator : Generator<Int> {
  override fun generate(): Int = Random.Default.nextInt(1..25)
}
