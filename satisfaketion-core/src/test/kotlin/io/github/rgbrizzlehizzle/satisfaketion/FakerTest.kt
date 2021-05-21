package io.github.rgbrizzlehizzle.satisfaketion

import io.github.rgbrizzlehizzle.satisfaketion.util.SimpleDataClass
import io.kotest.core.spec.style.DescribeSpec

class FakerTest : DescribeSpec({
  describe("Faker Core Functionality") {
    it("Can be instantiated around a data class") {
      Faker<SimpleDataClass> {
        SimpleDataClass::a { }
        SimpleDataClass::b { }
      }
    }
  }
})
