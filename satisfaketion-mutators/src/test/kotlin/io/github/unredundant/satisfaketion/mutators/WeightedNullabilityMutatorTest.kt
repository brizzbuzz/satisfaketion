package io.github.unredundant.satisfaketion.mutators

import io.github.unredundant.satisfaketion.core.Extensions.mutate
import io.github.unredundant.satisfaketion.core.Generator
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.shouldBe
import kotlin.random.Random
import org.junit.jupiter.api.assertThrows

class WeightedNullabilityMutatorTest : DescribeSpec({
  it("Can mutate an existing generator") {
    // arrange
    val random = Random(42)
    val generator = simpleGenerator(random)

    // act
    val mutated = generator.mutate(WeightedNullabilityMutator(0.5, random))
    val results = IntRange(0, 100).map { mutated.generate(random) }

    // assert
    results.filterNotNull().count() shouldBeExactly 47
  }
  it("Provides results near approximate weight") {
    // arrange
    val random = Random(42)
    val generator = simpleGenerator(random)

    // act
    val mutated = generator.mutate(WeightedNullabilityMutator(0.1, random))
    val results = IntRange(0, 100).map { mutated.generate(random) }

    // assert
    results.filterNotNull().count() shouldBeExactly 7
  }
  it("Cannot declare a weight over 1") {
    // arrange
    val random = Random(42)
    val generator = simpleGenerator(random)

    // act
    val result = assertThrows<IllegalArgumentException> {
      generator.mutate(WeightedNullabilityMutator(1.5, random))
    }

    // assert
    result.message shouldBe "Weight 1.5 invalid: must be between 0 and 1"
  }
  it("Cannot declare a weight of 0") {
    // arrange
    val random = Random(42)
    val generator = simpleGenerator(random)

    // act
    val result = assertThrows<IllegalArgumentException> {
      generator.mutate(WeightedNullabilityMutator(0.0, random))
    }

    // assert
    result.message shouldBe "Weight 0.0 invalid: must be between 0 and 1"
  }
}) {
  companion object {
    fun simpleGenerator(random: Random): Generator<Int> = Generator { random.nextInt() }
  }
}
