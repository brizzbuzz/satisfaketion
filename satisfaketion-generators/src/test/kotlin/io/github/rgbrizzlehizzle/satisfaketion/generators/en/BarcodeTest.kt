package io.github.rgbrizzlehizzle.satisfaketion.generators.en

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import kotlin.random.Random

class BarcodeTest : DescribeSpec({
  it("Can generate an ean8") {
    // arrange
    val seed = 123
    val barcode = Barcode(Random(seed))

    // act
    val result = barcode.ean8.generate()

    // assert
    result shouldBe "6583760"
  }
  it("Produces deterministic results") {
    // arrange
    val seed = 12345
    val barcodeA = Barcode(Random(seed))
    val barcodeB = Barcode(Random(seed))

    // act
    val resultA = barcodeA.compositeSymbol.generate()
    val resultB = barcodeB.compositeSymbol.generate()

    // assert
    resultA shouldBe resultB
    resultA shouldBe "18SB06JH"
  }
})
