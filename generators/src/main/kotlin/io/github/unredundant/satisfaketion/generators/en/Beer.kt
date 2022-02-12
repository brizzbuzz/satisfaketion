package io.github.unredundant.satisfaketion.generators.en

import io.github.unredundant.satisfaketion.core.Extensions.nextItem
import io.github.unredundant.satisfaketion.core.Generator
import io.github.unredundant.satisfaketion.generators.common.Utils
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

object Beer {

  @Serializable
  data class BeerMetadata(
    val brands: List<String>,
    val names: List<String>,
    val hops: List<String>,
    val yeasts: List<String>,
    val malts: List<String>,
    val styles: List<String>,
  )

  private val json = Utils.getFile("beer.json")
  private val metadata = Json.decodeFromString<BeerMetadata>(json)

  val brand = Generator { r -> metadata.brands.nextItem(r) }

  val name = Generator { r -> metadata.names.nextItem(r) }

  val hop = Generator { r -> metadata.hops.nextItem(r) }

  val yeast = Generator { r -> metadata.yeasts.nextItem(r) }

  val malt = Generator { r -> metadata.malts.nextItem(r) }

  val style = Generator { r -> metadata.styles.nextItem(r) }
}
