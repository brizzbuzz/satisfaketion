package io.github.rgbrizzlehizzle.satisfaketion.generators.en

import com.charleskorn.kaml.Yaml
import io.github.rgbrizzlehizzle.satisfaketion.core.Extensions.nextItem
import io.github.rgbrizzlehizzle.satisfaketion.core.Generator
import io.github.rgbrizzlehizzle.satisfaketion.generators.common.Name
import io.github.rgbrizzlehizzle.satisfaketion.generators.common.Utils
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString

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

  private val yaml = Utils.getFile("english_names.yaml")
  private val metadata = Yaml.default.decodeFromString<EnglishNameMetadata>(yaml)

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
