package io.github.rgbrizzlehizzle.satisfaketion.core

import io.github.rgbrizzlehizzle.satisfaketion.core.util.AnotherSimpleClass
import io.github.rgbrizzlehizzle.satisfaketion.core.util.SimpleDataClass
import io.github.rgbrizzlehizzle.satisfaketion.core.util.SmolIntGenerator
import io.github.rgbrizzlehizzle.satisfaketion.core.util.TestPhoneGenerator
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.ints.shouldBeGreaterThanOrEqual
import io.kotest.matchers.ints.shouldBeLessThanOrEqual
import io.kotest.matchers.maps.shouldContainKey
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldMatch
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
        SimpleDataClass::a { Generator { "string" } }
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
        SimpleDataClass::a { Generator { "test" } }
      }
      val satisfaketion = satisfaketion {
        register(SimpleDataClass::class, faker)
      }

      // act
      val result = assertThrows<IllegalArgumentException> {
        satisfaketion.register(SimpleDataClass::class, faker)
      }

      // assert
      result.message shouldStartWith "class io.github.rgbrizzlehizzle.satisfaketion.core.util.SimpleDataClass"
    }
    it("Can declare a faker inline") {
      // act
      val result = satisfaketion {
        register(SimpleDataClass::class) {
          SimpleDataClass::b { Generator { 123 } }
        }
      }

      // assert
      result.fakes.size shouldBeExactly 1
      result.fakes shouldContainKey SimpleDataClass::class
    }
  }
  describe("Fakes can be generated") {
    it("Can generate from a registered fake") {
      // arrange
      val satisfaketion = satisfaketion {
        register(SimpleDataClass::class) {
          SimpleDataClass::a { TestPhoneGenerator }
          SimpleDataClass::b { SmolIntGenerator }
        }
      }

      // act
      val result = satisfaketion.generate<SimpleDataClass>()

      // assert
      result shouldNotBe null
      result.a shouldMatch "^[1-9]\\d{2}-\\d{3}-\\d{4}"
      result.b shouldBeLessThanOrEqual 25
      result.b shouldBeGreaterThanOrEqual 1
    }
    it("Throws an error when generate called on unregistered class") {
      // arrange
      val satisfaketion = satisfaketion {
        register(SimpleDataClass::class) {
          SimpleDataClass::a { TestPhoneGenerator }
          SimpleDataClass::b { SmolIntGenerator }
        }
      }

      // act
      val result = assertThrows<IllegalStateException> {
        satisfaketion.generate<AnotherSimpleClass>()
      }

      // assert
      result.message shouldStartWith "No registered faker for class io.github.rgbrizzlehizzle.satisfaketion.core.util.AnotherSimpleClass"
    }
  }
})
