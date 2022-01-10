package io.github.unredundant.satisfaketion.core

import kotlin.random.Random

fun interface Generator<R> {
  fun generate(seed: Random): R
}
