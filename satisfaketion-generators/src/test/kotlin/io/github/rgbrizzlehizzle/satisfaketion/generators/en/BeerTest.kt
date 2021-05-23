package io.github.rgbrizzlehizzle.satisfaketion.generators.en

import io.github.rgbrizzlehizzle.satisfaketion.core.satisfaketion
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlin.random.Random

class BeerTest : DescribeSpec({
  it("Can generate a brand") {
    // arrange
    val seed = 123
    val beer = Beer(Random(seed))

    // act
    val result = beer.brand.generate()

    // assert
    result shouldBe "Budweiser"
  }
  it("Can populate an entire data class") {
    // arrange
    val seed = 555
    val beer = Beer(Random(seed))
    val satisfaketion = satisfaketion {
      register(DasBeer::class) {
        DasBeer::brand { beer.brand }
        DasBeer::name { beer.name }
        DasBeer::hop { beer.hop }
        DasBeer::yeast { beer.yeast }
        DasBeer::malt { beer.malt }
        DasBeer::style { beer.style }
      }
    }

    // act
    val result = satisfaketion.generate<DasBeer>()

    // assert
    val expected = DasBeer(
      brand = "Murphys",
      name = "Sierra Nevada Celebration Ale",
      hop = "Galena",
      yeast = "1318 - London Ale III",
      malt = "Chocolate malt",
      style = "Dark Lager",
    )
    result shouldBe expected
  }
  it("Generates different results when called consecutively") {
    // arrange
    val seed = 555
    val beer = Beer(Random(seed))
    val satisfaketion = satisfaketion {
      register(DasBeer::class) {
        DasBeer::brand { beer.brand }
        DasBeer::name { beer.name }
        DasBeer::hop { beer.hop }
        DasBeer::yeast { beer.yeast }
        DasBeer::malt { beer.malt }
        DasBeer::style { beer.style }
      }
    }

    // act
    val resultA = satisfaketion.generate<DasBeer>()
    val resultB = satisfaketion.generate<DasBeer>()

    // assert
    resultA shouldNotBe resultB
  }
}) {
  data class DasBeer(
    val brand: String,
    val name: String,
    val hop: String,
    val yeast: String,
    val malt: String,
    val style: String
  )
}
