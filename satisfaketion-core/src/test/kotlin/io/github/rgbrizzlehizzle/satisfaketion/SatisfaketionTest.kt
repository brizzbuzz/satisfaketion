package io.github.rgbrizzlehizzle.satisfaketion

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlin.random.Random

class SatisfaketionTest : DescribeSpec({
  describe("Class can be instantiated with desired local and seed") {
    it("Is instantiated when using the class itself") {
      // act
      val result = Satisfaketion(locale = "tr", random = Random(123))

      // assert
      result shouldNotBe null
      result.locale shouldBe "tr"
    }
    it("Can be instantiated with default params") {
      // act
      val result = Satisfaketion()

      // assert
      result shouldNotBe null
      result.random shouldBe Random.Default
    }
    it("Can be instantiated with overridden values via function") {
      // act
      val result = satisfaketion {
        locale = "fr-CA"
      }

      // assert
      result shouldNotBe null
      result.locale shouldBe "fr-CA"
    }
  }
})
