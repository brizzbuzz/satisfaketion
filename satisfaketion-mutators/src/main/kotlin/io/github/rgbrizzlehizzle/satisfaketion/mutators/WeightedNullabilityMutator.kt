package io.github.rgbrizzlehizzle.satisfaketion.mutators

import io.github.rgbrizzlehizzle.satisfaketion.core.Generator
import io.github.rgbrizzlehizzle.satisfaketion.core.Mutator
import kotlin.random.Random

class WeightedNullabilityMutator<T : Any>(
  private val weight: Double,
  private val random: Random = Random.Default
) : Mutator<T, T?> {

  init {
    require(weight > 0 && weight < 1) { "Weight $weight invalid: must be between 0 and 1" }
  }

  override fun mutate(generator: Generator<T>): Generator<T?> = Generator {
    val baseResult = generator.generate(random)
    val picker = (random.nextInt(MAX_WEIGHT).toDouble()) / MAX_WEIGHT
    when (weight > picker) {
      false -> null
      true -> baseResult
    }
  }

  private companion object {
    const val MAX_WEIGHT = 100
  }
}
