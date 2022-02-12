package io.github.unredundant.satisfaketion.generators.en

import io.github.unredundant.satisfaketion.core.Extensions.nextItem
import io.github.unredundant.satisfaketion.core.Generator
import io.github.unredundant.satisfaketion.generators.common.Name
import io.github.unredundant.satisfaketion.generators.common.Reader
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okio.Path.Companion.toPath

object EnglishName : Name {

  @Serializable
  data class EnglishNameMetadata(
    val maleFirstNames: List<String>,
    val femaleFirstNames: List<String>,
    val neutralFirstNames: List<String>,
    val lastNames: List<String>,
    val prefixes: List<String>,
    val suffixes: List<String>
  )

  private val json = Reader.read("src/resources/english_names.json".toPath())
  private val metadata = Json.decodeFromString<EnglishNameMetadata>(json)

  override val maleFirstName: Generator<String> = Generator { r -> metadata.maleFirstNames.nextItem(r) }
  override val femaleFirstName: Generator<String> = Generator { r -> metadata.femaleFirstNames.nextItem(r) }
  override val neutralFirstName: Generator<String> = Generator { r -> metadata.neutralFirstNames.nextItem(r) }

  override val firstName: Generator<String> = Generator { r ->
    val formats = listOf(maleFirstName, femaleFirstName, neutralFirstName)
    formats.nextItem(r).generate(r)
  }

  override val lastName: Generator<String> = Generator { r -> metadata.lastNames.nextItem(r) }

  override val prefix: Generator<String> = Generator { r -> metadata.prefixes.nextItem(r) }

  override val suffix: Generator<String> = Generator { r -> metadata.suffixes.nextItem(r) }
}
