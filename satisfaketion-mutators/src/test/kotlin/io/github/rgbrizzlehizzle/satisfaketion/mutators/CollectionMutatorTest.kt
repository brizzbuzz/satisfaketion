package io.github.rgbrizzlehizzle.satisfaketion.mutators

import io.github.rgbrizzlehizzle.satisfaketion.core.Generator
import io.github.rgbrizzlehizzle.satisfaketion.core.mutate
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.shouldBe
import kotlin.random.Random
import org.junit.jupiter.api.assertThrows

class CollectionMutatorTest : DescribeSpec({
  it("Can mutate an existing generator") {
    // arrange
    val random = Random(42)
    val generator = simpleGenerator(random)

    // act
    val mutated = generator.mutate(CollectionMutator(5, 100, random))
    val results = mutated.generate()

    // assert
    results.count() shouldBeExactly 24
  }
  it("Throws an error when min length is less than 0") {
    // arrange
    val random = Random(42)
    val generator = simpleGenerator(random)

    // act
    val result = assertThrows<IllegalArgumentException> {
      generator.mutate(CollectionMutator(-10, 10, random))
    }

    // assert
    result.message shouldBe "Invalid min length -10: must be greater than 0"
  }
  it("Throws an error when mix length is greater than max length") {
    // arrange
    val random = Random(42)
    val generator = simpleGenerator(random)

    // act
    val result = assertThrows<IllegalArgumentException> {
      generator.mutate(CollectionMutator(100, 10, random))
    }

    // assert
    result.message shouldBe "Invalid min 100 max 10: min must be less than max"
  }
  it("can produce an empty list") {
    // arrange
    val random = Random(42)
    val generator = simpleGenerator(random)

    // act
    val mutated = generator.mutate(CollectionMutator(0, 0, random))
    val results = mutated.generate()

    // assert
    results.count() shouldBeExactly 0
  }
}) {
  companion object {
    fun simpleGenerator(random: Random): Generator<Int> = Generator { random.nextInt() }
  }
}
