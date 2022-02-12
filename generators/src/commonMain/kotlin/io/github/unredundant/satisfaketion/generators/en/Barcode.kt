package io.github.unredundant.satisfaketion.generators.en

import io.github.unredundant.satisfaketion.core.Extensions.letterify
import io.github.unredundant.satisfaketion.core.Extensions.nextItem
import io.github.unredundant.satisfaketion.core.Extensions.numerify
import io.github.unredundant.satisfaketion.core.Generator
import io.github.unredundant.satisfaketion.generators.common.Reader
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okio.Path.Companion.toPath

object Barcode {

  @Serializable
  data class BarcodeMetadata(
    val upcEs: List<String>,
    val compositeSymbols: List<String>,
    val isbns: List<String>,
  )

  private val json = Reader.read("src/resources/barcode.json".toPath())
  private val metadata = Json.decodeFromString<BarcodeMetadata>(json)

  val ean8 = Generator { r -> "#######".numerify(r) }

  val ean13 = Generator { r -> "############".numerify(r) }

  val upcA = Generator { r -> "###########".numerify(r) }

  val upcE = Generator { r -> metadata.upcEs.nextItem(r).numerify(r) }

  val compositeSymbol = Generator { r -> metadata.compositeSymbols.nextItem(r).numerify(r).letterify(r) }

  val isbn = Generator { r -> metadata.isbns.nextItem(r).numerify(r) }

  val ismn = Generator { r -> "9790########".numerify(r) }

  val issn = Generator { r -> "977#########".numerify(r) }
}
