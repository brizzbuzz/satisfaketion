package io.github.rgbrizzlehizzle.satisfaketion.generators.mutators

import io.github.rgbrizzlehizzle.satisfaketion.core.mutate
import io.github.rgbrizzlehizzle.satisfaketion.generators.en.EnglishName
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.shouldBe
import kotlin.random.Random
import org.junit.jupiter.api.assertThrows

class WeightedNullabilityMutatorTest : DescribeSpec({
  it("Can mutate an existing generator") {
    // arrange
    val random = Random(42)
    val names = EnglishName(random)

    // act
    val mutated = names.firstName.mutate(WeightedNullabilityMutator(0.5, random))
    val results = IntRange(0, 100).map { mutated.generate() }

    // assert
    results.filterNotNull().count() shouldBeExactly 50
  }
  it("Provides results near approximate weight") {
    // arrange
    val random = Random(42)
    val names = EnglishName(random)

    // act
    val mutated = names.firstName.mutate(WeightedNullabilityMutator(0.1, random))
    val results = IntRange(0, 100).map { mutated.generate() }

    // assert
    results.filterNotNull().count() shouldBeExactly 9
  }
  it("Cannot declare a weight over 1") {
    // arrange
    val random = Random(42)
    val names = EnglishName(random)

    // act
    val result = assertThrows<IllegalArgumentException> {
      names.firstName.mutate(WeightedNullabilityMutator(1.5, random))
    }

    // assert
    result.message shouldBe "Weight 1.5 invalid: must be between 0 and 1"
  }
  it("Cannot declare a weight of 0") {
    // arrange
    val random = Random(42)
    val names = EnglishName(random)

    // act
    val result = assertThrows<IllegalArgumentException> {
      names.firstName.mutate(WeightedNullabilityMutator(0.0, random))
    }

    // assert
    result.message shouldBe "Weight 0.0 invalid: must be between 0 and 1"
  }
})
