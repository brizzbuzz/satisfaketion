package io.github.rgbrizzlehizzle.satisfaketion.generators.en

import com.charleskorn.kaml.Yaml
import io.github.rgbrizzlehizzle.satisfaketion.core.Generator
import io.github.rgbrizzlehizzle.satisfaketion.core.nextItem
import io.github.rgbrizzlehizzle.satisfaketion.generators.common.Name
import io.github.rgbrizzlehizzle.satisfaketion.generators.common.Utils
import kotlin.random.Random
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString

class EnglishName(private val random: Random = Random.Default) : Name {

  private companion object {
    @Serializable
    data class EnglishNameMetadata(
      val maleFirstNames: List<String>,
      val femaleFirstNames: List<String>,
      val neutralFirstNames: List<String>,
      val lastNames: List<String>,
      val prefixes: List<String>,
      val suffixes: List<String>
    )
  }

  private val yaml = Utils.getFile("english_names.yaml")
  private val metadata = Yaml.default.decodeFromString<EnglishNameMetadata>(yaml)

  override val maleFirstName: Generator<String> = Generator { metadata.maleFirstNames.nextItem(random) }
  override val femaleFirstName: Generator<String> = Generator { metadata.femaleFirstNames.nextItem(random) }
  override val neutralFirstName: Generator<String> = Generator { metadata.neutralFirstNames.nextItem(random) }

  override val firstName: Generator<String> = Generator {
    val formats = listOf(maleFirstName, femaleFirstName, neutralFirstName)
    formats.nextItem(random).generate()
  }

  override val lastName: Generator<String> = Generator { metadata.lastNames.nextItem(random) }

  override val prefix: Generator<String> = Generator { metadata.prefixes.nextItem(random) }

  override val suffix: Generator<String> = Generator { metadata.suffixes.nextItem(random) }
}
