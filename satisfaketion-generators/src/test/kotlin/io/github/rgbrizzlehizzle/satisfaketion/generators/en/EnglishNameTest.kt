package io.github.rgbrizzlehizzle.satisfaketion.generators.en

import io.github.rgbrizzlehizzle.satisfaketion.core.Extensions.mutate
import io.github.rgbrizzlehizzle.satisfaketion.core.satisfaketion
import io.github.rgbrizzlehizzle.satisfaketion.mutators.WeightedNullabilityMutator
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlin.random.Random

class EnglishNameTest : DescribeSpec({
  it("Can generate a person object") {
    // arrange
    val seed = Random(42)
    val satisfaketion = satisfaketion {
      register(MyPerson::class) {
        MyPerson::firstName { EnglishName.firstName }
        MyPerson::lastName { EnglishName.lastName }
        MyPerson::prefix { EnglishName.prefix.mutate(WeightedNullabilityMutator(0.25, seed)) }
        MyPerson::suffix { EnglishName.suffix.mutate(WeightedNullabilityMutator(0.25, seed)) }
      }
    }

    // act
    val result = satisfaketion.generate<MyPerson>(seed)

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
