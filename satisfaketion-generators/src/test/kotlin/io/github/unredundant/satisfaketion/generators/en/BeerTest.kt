package io.github.unredundant.satisfaketion.generators.en

import io.github.unredundant.satisfaketion.core.satisfaketion
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import kotlin.random.Random

class BeerTest : DescribeSpec({
  it("Can generate a brand") {
    // arrange
    val seed = 123

    // act
    val result = Beer.brand.generate(Random(seed))

    // assert
    result shouldBe "Budweiser"
  }
  it("Can populate an entire data class") {
    // arrange
    val seed = 555
    val satisfaketion = satisfaketion {
      register(DasBeer::class) {
        DasBeer::brand { Beer.brand }
        DasBeer::name { Beer.name }
        DasBeer::hop { Beer.hop }
        DasBeer::yeast { Beer.yeast }
        DasBeer::malt { Beer.malt }
        DasBeer::style { Beer.style }
      }
    }

    // act
    val result = satisfaketion.generate<DasBeer>(Random(seed))

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
    val seed = Random(555)
    val satisfaketion = satisfaketion {
      register(DasBeer::class) {
        DasBeer::brand { Beer.brand }
        DasBeer::name { Beer.name }
        DasBeer::hop { Beer.hop }
        DasBeer::yeast { Beer.yeast }
        DasBeer::malt { Beer.malt }
        DasBeer::style { Beer.style }
      }
    }

    // act
    val resultA = satisfaketion.generate<DasBeer>(seed)
    val resultB = satisfaketion.generate<DasBeer>(seed)

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
