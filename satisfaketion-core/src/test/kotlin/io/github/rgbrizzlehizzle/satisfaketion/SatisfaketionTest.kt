package io.github.rgbrizzlehizzle.satisfaketion

import io.github.rgbrizzlehizzle.satisfaketion.util.SimpleDataClass
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.maps.shouldContainKey
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldStartWith
import kotlin.random.Random
import org.junit.jupiter.api.assertThrows

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
  describe("Fakes can be registered") {
    it("can register a new fake") {
      // arrange
      val faker = Faker<SimpleDataClass> {
        SimpleDataClass::a { }
      }

      // act
      val result = Satisfaketion()
      result.register(SimpleDataClass::class, faker)

      // assert
      result.fakes.size shouldBeExactly 1
      result.fakes shouldContainKey SimpleDataClass::class
    }
    it("throws an exception when overriding a registered fake") {
      // arrange
      val faker = Faker<SimpleDataClass> {
        SimpleDataClass::a { }
      }
      val satisfaketion = satisfaketion {
        register(SimpleDataClass::class, faker)
      }

      // act
      val result = assertThrows<IllegalArgumentException> {
        satisfaketion.register(SimpleDataClass::class, faker)
      }

      // assert
      result.message shouldStartWith "class io.github.rgbrizzlehizzle.satisfaketion.util.SimpleDataClass"
    }
    it("Can declare a faker inline") {
      // act
      val result = satisfaketion {
        register(SimpleDataClass::class) {
          SimpleDataClass::b { }
        }
      }

      // assert
      result.fakes.size shouldBeExactly 1
      result.fakes shouldContainKey SimpleDataClass::class
    }
  }
})
