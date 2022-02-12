package io.github.unredundant.satisfaketion.generators.en

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import kotlin.random.Random

class BeerTest : DescribeSpec({
  describe("Beer") {
    it("Can generate a beer brand") {
      // act
      val brand = Beer.brand.generate(getSeed())

      // assert
      brand shouldBe "Stella Artois"
    }
    it("Can generate a beer name") {
      // act
      val brand = Beer.name.generate(getSeed())

      // assert
      brand shouldBe "Ruination IPA"
    }
    it("Can generate some hops") {
      // act
      val brand = Beer.hop.generate(getSeed())

      // assert
      brand shouldBe "Centennial"
    }
    it("Can generate some yeasts") {
      // act
      val brand = Beer.yeast.generate(getSeed())

      // assert
      brand shouldBe "1762 - Belgian Abbey II"
    }
    it("Can generate some malts") {
      // act
      val brand = Beer.malt.generate(getSeed())

      // assert
      brand shouldBe "Chocolate"
    }
    it("Can generate some style") {
      // act
      val brand = Beer.style.generate(getSeed())

      // assert
      brand shouldBe "European Amber Lager"
    }
  }
}) {
  companion object {
    val getSeed = { Random(42) }
  }
}
