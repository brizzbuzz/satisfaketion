package io.github.rgbrizzlehizzle.satisfaketion.generators.en

import com.charleskorn.kaml.Yaml
import io.github.rgbrizzlehizzle.satisfaketion.core.Generator
import io.github.rgbrizzlehizzle.satisfaketion.core.nextItem
import io.github.rgbrizzlehizzle.satisfaketion.core.numerify
import io.github.rgbrizzlehizzle.satisfaketion.generators.common.Address
import io.github.rgbrizzlehizzle.satisfaketion.generators.common.Utils
import kotlin.random.Random
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString

class UnitedStatesAddress(private val random: Random = Random.Default) : Address {

  private companion object {
    @Serializable
    data class UnitedStatesAddressMetadata(
      val cityPrefix: List<String>,
      val citySuffix: List<String>,
      val streetSuffix: List<String>,
      val communityPrefix: List<String>,
      val communitySuffix: List<String>,
      val postcodesByState: Map<String, String>,
      val states: List<String>,
      val stateCodes: List<String>
    )
  }

  private val yaml = Utils.getFile("united_states_address.yaml")
  private val metadata = Yaml.default.decodeFromString<UnitedStatesAddressMetadata>(yaml)
  private val nameGenerator = EnglishName(random)

  override val name: String = "United States"
  override val code: String = "USA"

  override val buildingNumber: Generator<Int> = Generator {
    val buildingNumbers = listOf("#####", "####", "###")
    val format = buildingNumbers.nextItem(random)
    format.numerify(random).toInt()
  }

  override val community: Generator<String> = Generator {
    "${metadata.communityPrefix.nextItem(random)} ${metadata.communitySuffix.nextItem(random)}"
  }

  override val secondaryAddress: Generator<String> = Generator {
    val formats = listOf("Apt. ###", "Suite ###")
    formats.nextItem(random).numerify(random)
  }

  override val postcode: Generator<String> = Generator { "#####".numerify(random) }

  val postCodeWithLocal: Generator<String> = Generator { "#####-####".numerify(random) }

  fun postcodeByState(stateCode: String, local: Boolean = false): Generator<String> = Generator {
    val state = metadata.postcodesByState[stateCode] ?: error("Invalid state code $stateCode provided")
    val postCode = state.numerify(random)
    if (local) {
      postCode.plus("-####").numerify(random)
    } else {
      postCode
    }
  }

  val state: Generator<String> = Generator { metadata.states.nextItem(random) }

  val stateCode: Generator<String> = Generator { metadata.stateCodes.nextItem(random) }

  override val timeZone: Generator<String> = Generator {
    val timezones = listOf("HAT", "AST", "PT", "MT", "CT", "ET")
    timezones.nextItem(random)
  }

  override val city: Generator<String> = Generator { cityStreetGenerator(metadata.citySuffix.nextItem(random)) }

  override val streetName: Generator<String> =
    Generator { cityStreetGenerator(" ".plus(metadata.streetSuffix.nextItem(random))) }

  private fun cityStreetGenerator(suffix: String): String {
    val sb = StringBuilder()
    when (random.nextBoolean()) {
      true -> sb.append(metadata.cityPrefix.nextItem(random).plus(" "))
      false -> Unit
    }
    when (random.nextBoolean()) {
      true -> sb.append("${nameGenerator.firstName.generate()}$suffix")
      false -> sb.append("${nameGenerator.lastName.generate()}$suffix")
    }
    return sb.toString()
  }

  override val streetAddress: Generator<String> = Generator { "${buildingNumber.generate()} ${streetName.generate()}" }

  override val fullAddress: Generator<String> = Generator {
    val sb = StringBuilder()
    sb.append(streetAddress.generate()).append(", ")
    when (random.nextBoolean()) {
      true -> sb.append(secondaryAddress.generate()).append(", ")
    }
    sb.append(city.generate()).append(", ")
    val sc = stateCode.generate()
    sb.append(sc).append(" ")
    sb.append(postcodeByState(sc).generate())
    sb.toString()
  }

  override val mailbox: Generator<String> = Generator {
    val formats = listOf("PO Box ##", "PO Box ###", "PO Box ####")
    formats.nextItem(random).numerify(random)
  }
}
