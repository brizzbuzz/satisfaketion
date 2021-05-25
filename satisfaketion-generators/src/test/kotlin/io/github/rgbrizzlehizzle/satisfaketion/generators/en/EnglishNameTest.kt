package io.github.rgbrizzlehizzle.satisfaketion.generators.en

import io.github.rgbrizzlehizzle.satisfaketion.core.Generator
import io.github.rgbrizzlehizzle.satisfaketion.core.satisfaketion
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlin.random.Random

class EnglishNameTest : DescribeSpec({
  it("Can generate a person object") {
    // arrange
    val seed = Random(42)
    val nameGenerator = EnglishName(seed)
    val satisfaketion = satisfaketion {
      register(MyPerson::class) {
        MyPerson::firstName { nameGenerator.firstName }
        MyPerson::lastName { nameGenerator.lastName }
        MyPerson::prefix { nameGenerator.prefix as Generator<String?> }
        MyPerson::suffix { nameGenerator.suffix as Generator<String?> }
      }
    }

    // act
    val result = satisfaketion.generate<MyPerson>()

    // assert
    result shouldNotBe null
    result shouldBe MyPerson(
      firstName = "Phoenix",
      lastName = "Howe",
      prefix = "Ms.",
      suffix = "LLD"
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
