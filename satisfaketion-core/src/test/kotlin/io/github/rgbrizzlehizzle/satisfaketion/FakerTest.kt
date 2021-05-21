package io.github.rgbrizzlehizzle.satisfaketion

import io.github.rgbrizzlehizzle.satisfaketion.util.AnotherSimpleClass
import io.github.rgbrizzlehizzle.satisfaketion.util.SimpleDataClass
import io.kotest.assertions.assertSoftly
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.string.shouldStartWith
import org.junit.jupiter.api.assertThrows

class FakerTest : DescribeSpec({
  describe("Faker Core Functionality") {
    it("Can be instantiated around a data class") {
      // act
      val fake = Faker<SimpleDataClass> {
        SimpleDataClass::a { }
        SimpleDataClass::b { }
      }

      // assert
      fake shouldNotBe null
    }
    it("Can track invoked properties") {
      // arrange
      val fakeA = Faker<SimpleDataClass> {
        SimpleDataClass::a { }
        SimpleDataClass::b { }
      }
      val fakeB = Faker<AnotherSimpleClass> {
        AnotherSimpleClass::c { }
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
          SimpleDataClass::a { }
          SimpleDataClass::a { }
        }
      }
      exception.message shouldStartWith "a has already been registered"
    }
  }
})
