package io.github.rgbrizzlehizzle.satisfaketion.mutators

import io.github.rgbrizzlehizzle.satisfaketion.core.Generator
import io.github.rgbrizzlehizzle.satisfaketion.core.Mutator
import kotlin.random.Random

class CollectionMutator<T : Any>(
  private val minLength: Int = 0,
  private val maxLength: Int = 10,
  private val random: Random = Random.Default
) : Mutator<T, List<T>> {

  init {
    require(minLength >= 0) { "Invalid min length $minLength: must be greater than 0" }
    require(minLength <= maxLength) { "Invalid min $minLength max $maxLength: min must be less than max" }
  }

  override fun mutate(generator: Generator<T>): Generator<List<T>> = Generator {
    if (maxLength == 0) {
      emptyList()
    } else {
      val length = random.nextInt(minLength, maxLength)
      if (length == 0) {
        emptyList()
      } else {
        IntRange(0, length).map { generator.generate(random) }
      }
    }
  }
}
