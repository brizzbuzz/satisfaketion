package io.github.unredundant.satisfaketion.generators.en

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import kotlin.random.Random

class BarcodeTest : DescribeSpec({
  describe("Barcode") {
    it("Can generate an ean8") {
      // arrange
      val seed = 123

      // act
      val result = Barcode.ean8.generate(Random(seed))

      // assert
      result shouldBe "6583760"
    }
    it("Produces deterministic results") {
      // arrange
      val seed = 12345

      // act
      val resultA = Barcode.compositeSymbol.generate(Random(seed))
      val resultB = Barcode.compositeSymbol.generate(Random(seed))

      // assert
      resultA shouldBe resultB
      resultA shouldBe "18SB06JH"
    }
  }
})
