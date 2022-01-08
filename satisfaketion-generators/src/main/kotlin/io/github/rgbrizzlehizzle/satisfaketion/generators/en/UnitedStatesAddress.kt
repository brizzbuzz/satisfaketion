package io.github.rgbrizzlehizzle.satisfaketion.generators.en

import com.charleskorn.kaml.Yaml
import io.github.rgbrizzlehizzle.satisfaketion.core.Extensions.nextItem
import io.github.rgbrizzlehizzle.satisfaketion.core.Extensions.numerify
import io.github.rgbrizzlehizzle.satisfaketion.core.Generator
import io.github.rgbrizzlehizzle.satisfaketion.generators.common.Address
import io.github.rgbrizzlehizzle.satisfaketion.generators.common.Utils
import kotlin.random.Random
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString

object UnitedStatesAddress : Address {

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

  private val yaml = Utils.getFile("united_states_address.yaml")
  private val metadata = Yaml.default.decodeFromString<UnitedStatesAddressMetadata>(yaml)

  override val name: String = "United States"
  override val code: String = "USA"

  override val buildingNumber: Generator<Int> = Generator { r ->
    val buildingNumbers = listOf("#####", "####", "###")
    val format = buildingNumbers.nextItem(r)
    format.numerify(r).toInt()
  }

  override val community: Generator<String> = Generator { r ->
    "${metadata.communityPrefix.nextItem(r)} ${metadata.communitySuffix.nextItem(r)}"
  }

  override val secondaryAddress: Generator<String> = Generator { r ->
    val formats = listOf("Apt. ###", "Suite ###")
    formats.nextItem(r).numerify(r)
  }

  override val postcode: Generator<String> = Generator { r -> "#####".numerify(r) }

  val postCodeWithLocal: Generator<String> = Generator { r -> "#####-####".numerify(r) }

  fun postcodeByState(stateCode: String, local: Boolean = false): Generator<String> = Generator { r ->
    val state = metadata.postcodesByState[stateCode] ?: error("Invalid state code $stateCode provided")
    val postCode = state.numerify(r)
    if (local) {
      postCode.plus("-####").numerify(r)
    } else {
      postCode
    }
  }

  val state: Generator<String> = Generator { r -> metadata.states.nextItem(r) }

  val stateCode: Generator<String> = Generator { r -> metadata.stateCodes.nextItem(r) }

  override val timeZone: Generator<String> = Generator { r ->
    val timezones = listOf("HAT", "AST", "PT", "MT", "CT", "ET")
    timezones.nextItem(r)
  }

  override val city: Generator<String> = Generator { r -> cityStreetGenerator(metadata.citySuffix.nextItem(r), r) }

  override val streetName: Generator<String> =
    Generator { r -> cityStreetGenerator(" ".plus(metadata.streetSuffix.nextItem(r)), r) }

  private fun cityStreetGenerator(suffix: String, seed: Random): String {
    val sb = StringBuilder()
    when (seed.nextBoolean()) {
      true -> sb.append(metadata.cityPrefix.nextItem(seed).plus(" "))
      false -> Unit
    }
    when (seed.nextBoolean()) {
      true -> sb.append("${EnglishName.firstName.generate(seed)}$suffix")
      false -> sb.append("${EnglishName.lastName.generate(seed)}$suffix")
    }
    return sb.toString()
  }

  override val streetAddress: Generator<String> =
    Generator { r -> "${buildingNumber.generate(r)} ${streetName.generate(r)}" }

  override val fullAddress: Generator<String> = Generator { r ->
    val sb = StringBuilder()
    sb.append(streetAddress.generate(r)).append(", ")
    when (r.nextBoolean()) {
      true -> sb.append(secondaryAddress.generate(r)).append(", ")
      else -> {}
    }
    sb.append(city.generate(r)).append(", ")
    val sc = stateCode.generate(r)
    sb.append(sc).append(" ")
    sb.append(postcodeByState(sc).generate(r))
    sb.toString()
  }

  override val mailbox: Generator<String> = Generator { r ->
    val formats = listOf("PO Box ##", "PO Box ###", "PO Box ####")
    formats.nextItem(r).numerify(r)
  }
}
