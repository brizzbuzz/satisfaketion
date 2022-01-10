package io.github.unredundant.satisfaketion.generators.en

import io.github.unredundant.satisfaketion.core.Extensions.mutate
import io.github.unredundant.satisfaketion.core.Faker
import io.github.unredundant.satisfaketion.mutators.WeightedNullabilityMutator
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlin.random.Random

class EnglishNameTest : DescribeSpec({
  it("Can generate a person object") {
    // arrange
    val seed = Random(42)
    val faker = Faker<MyPerson> {
      MyPerson::firstName { EnglishName.firstName }
      MyPerson::lastName { EnglishName.lastName }
      MyPerson::prefix { EnglishName.prefix.mutate(WeightedNullabilityMutator(0.25, seed)) }
      MyPerson::suffix { EnglishName.suffix.mutate(WeightedNullabilityMutator(0.25, seed)) }
    }

    // act
    val result = faker.generate(seed)

    // assert
    result shouldNotBe null
    result shouldBe MyPerson(
      firstName = "Phoenix",
      lastName = "Howe",
      prefix = null,
      suffix = "PhD"
    )
  }
}) {
  data class MyPerson(
    val firstName: String,
    val lastName: String,
    val prefix: String?,
    val suffix: String?,
  )
}
