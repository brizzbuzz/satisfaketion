package io.github.unredundant.satisfaketion.core

import io.github.unredundant.satisfaketion.core.Extensions.letterify
import io.github.unredundant.satisfaketion.core.util.AnotherSimpleClass
import io.github.unredundant.satisfaketion.core.util.SimpleDataClass
import io.github.unredundant.satisfaketion.core.util.SmolIntGenerator
import io.github.unredundant.satisfaketion.core.util.TestPhoneGenerator
import io.github.unredundant.satisfaketion.core.util.TimingStuff
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.ints.shouldBeGreaterThanOrEqual
import io.kotest.matchers.ints.shouldBeLessThanOrEqual
import io.kotest.matchers.kotlinx.datetime.shouldBeBefore
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldMatch
import io.kotest.matchers.string.shouldStartWith
import kotlinx.datetime.LocalDateTime
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
    it("Can generate a faked object") {
      // arrange
      val fake = Faker<SimpleDataClass> {
        SimpleDataClass::a { TestPhoneGenerator }
        SimpleDataClass::b { SmolIntGenerator }
      }

      // act
      val result = fake.generate()

      // assert
      result shouldNotBe null
      result.a shouldMatch "^[1-9]\\d{2}-\\d{3}-\\d{4}"
      result.b shouldBeLessThanOrEqual 25
      result.b shouldBeGreaterThanOrEqual 1
    }
    it("Throws an error when not all members have registered a generator") {
      // arrange
      val badFake = Faker<SimpleDataClass> {
        SimpleDataClass::a { TestPhoneGenerator }
      }

      // act
      val result = assertThrows<IllegalArgumentException> {
        badFake.generate()
      }

      // assert
      result.message shouldStartWith "No argument provided for a required parameter: parameter #1 b of fun"
    }
    it("Can generate a class with a default parameter") {
      // arrange
      val fake = Faker<AnotherSimpleClass> {
        AnotherSimpleClass::c { Generator { true } }
      }

      // act
      val result = fake.generate()

      // assert
      result shouldNotBe null
      result.c shouldBe true
      result.d shouldBe "hey dude"
    }
    it("Can generate a class with an overridden default") {
      // arrange
      val fake = Faker<AnotherSimpleClass> {
        AnotherSimpleClass::c { Generator { true } }
        AnotherSimpleClass::d { TestPhoneGenerator }
      }

      // act
      val result = fake.generate()

      // assert
      result shouldNotBe null
      result.c shouldBe true
      result.d shouldNotBe "hey dude"
    }
  }
  describe("Correlated Generators") {
    it("Can perform a simple correlation") {
      // arrange
      val fake = Faker<SimpleDataClass> {
        SimpleDataClass::a { Generator { r -> "?".repeat(r.nextInt(100)).letterify(r) } }
        SimpleDataClass::b {
          CorrelatedPropertyGenerator(SimpleDataClass::a) { i, _ ->
            i.length
          }
        }
      }

      // act
      val result = fake.generate()

      // assert
      result.b shouldBeExactly result.a.length
    }
    it("Can nest multiple correlations") {
      // arrange
      val fake = Faker<TimingStuff> {
        TimingStuff::start {
          Generator { r ->
            LocalDateTime(
              year = r.nextInt(1995, 2022),
              monthNumber = r.nextInt(1, 12),
              dayOfMonth = r.nextInt(1, 28),
              hour = r.nextInt(1, 23),
              minute = r.nextInt(1, 59)
            )
          }
        }
        TimingStuff::end {
          CorrelatedPropertyGenerator(TimingStuff::start) { start, seed ->
            LocalDateTime(
              year = start.year.plus(seed.nextInt(5, 25)),
              monthNumber = seed.nextInt(1, 12),
              dayOfMonth = seed.nextInt(1, 28),
              hour = seed.nextInt(1, 23),
              minute = seed.nextInt(1, 59)
            )
          }
        }
        TimingStuff::middle {
          CorrelatedPropertyGenerator(TimingStuff::start) { start, seed ->
            CorrelatedPropertyGenerator(TimingStuff::end) { end, _ ->
              LocalDateTime(
                year = seed.nextInt(start.year + 1, end.year - 1),
                monthNumber = seed.nextInt(1, 12),
                dayOfMonth = seed.nextInt(1, 28),
                hour = seed.nextInt(1, 23),
                minute = seed.nextInt(1, 59)
              )
            }.generate(seed)
          }
        }
      }

      (0..1000).forEach { _ ->
        // act
        val result = fake.generate()

        // assert
        result.start shouldBeBefore result.middle
        result.middle shouldBeBefore result.end
      }
    }
  }
})
