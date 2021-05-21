package io.github.rgbrizzlehizzle.satisfaketion

import io.github.rgbrizzlehizzle.satisfaketion.util.AnotherSimpleClass
import io.github.rgbrizzlehizzle.satisfaketion.util.SimpleDataClass
import io.github.rgbrizzlehizzle.satisfaketion.util.SmolIntGenerator
import io.github.rgbrizzlehizzle.satisfaketion.util.TestPhoneGenerator
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.ints.shouldBeGreaterThanOrEqual
import io.kotest.matchers.ints.shouldBeLessThanOrEqual
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldMatch
import io.kotest.matchers.string.shouldStartWith
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlin.random.Random
import org.junit.jupiter.api.assertThrows

class FakerTest : DescribeSpec({
  describe("Faker Core Functionality") {
    it("Can be instantiated around a data class") {
      // act
      val fake = Faker<SimpleDataClass> {
        SimpleDataClass::a { Generator { "feas" } }
        SimpleDataClass::b { Generator { 324 } }
      }

      // assert
      fake shouldNotBe null
    }
    it("Can track invoked properties") {
      // arrange
      val fakeA = Faker<SimpleDataClass> {
        SimpleDataClass::a { Generator { "fe" } }
        SimpleDataClass::b { Generator { 123 } }
      }
      val fakeB = Faker<AnotherSimpleClass> {
        AnotherSimpleClass::c { Generator { true } }
      }

      // act
      val sizeA = fakeA.propertyMap.size
      val sizeB = fakeB.propertyMap.size

      // assert
      assertSoftly {
        sizeA shouldBeExactly 2
        sizeB shouldBeExactly 1
      }
    }
    it("Throws an error if a property is invoked multiple times") {
      // assert
      val exception = assertThrows<IllegalArgumentException> {
        Faker<SimpleDataClass> {
          SimpleDataClass::a { Generator { "nice" } }
          SimpleDataClass::a { Generator { "bad" } }
        }
      }
      exception.message shouldStartWith "a has already been registered"
    }
    it("Can generate a value from an attached generator") {
      // arrange
      val random = Random.Default
      val fake = Faker<SimpleDataClass> {
        SimpleDataClass::a { Generator { random.nextInt().toString() } }
      }

      // act
      val generator = fake.propertyMap["a"] ?: error("where o' where did my generator go?")
      val first = generator.generate()
      val second = generator.generate()

      // assert
      first shouldNotBe null
      second shouldNotBe null
      first.shouldBeInstanceOf<String>()
      second.shouldBeInstanceOf<String>()
      first shouldNotBe second
    }
    it("Can leverage a custom generator") {
      // arrange
      val fake = Faker<SimpleDataClass> {
        SimpleDataClass::a { TestPhoneGenerator() }
        SimpleDataClass::b { SmolIntGenerator() }
      }

      // act
      val generatorA = fake.propertyMap["a"] ?: error("where o' where did my generator go?")
      val generatorB = fake.propertyMap["b"] ?: error("where o' where did my generator go?")
      val a = generatorA.generate()
      val b = generatorB.generate()

      // assert
      a shouldNotBe null
      b shouldNotBe null
      a.shouldBeInstanceOf<String>()
      b.shouldBeInstanceOf<Int>()
      a shouldMatch "^[1-9]\\d{2}-\\d{3}-\\d{4}"
      b shouldBeLessThanOrEqual 25
      b shouldBeGreaterThanOrEqual 1
    }
  }
})
