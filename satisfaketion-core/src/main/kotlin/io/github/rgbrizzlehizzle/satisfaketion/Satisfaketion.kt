package io.github.rgbrizzlehizzle.satisfaketion

import kotlin.random.Random

class Satisfaketion(
  var locale: String = "en-us", // TODO Enum?
  var random: Random = Random.Default
)

fun satisfaketion(block: Satisfaketion.() -> Unit) = Satisfaketion().apply(block)
