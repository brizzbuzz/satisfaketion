package io.github.rgbrizzlehizzle.satisfaketion.generators.common

import io.github.rgbrizzlehizzle.satisfaketion.core.Generator

interface Address {
  val name: String
  val code: String // TODO Enum?
  val buildingNumber: Generator<Int>
  val community: Generator<String>
  val secondaryAddress: Generator<String>
  val postcode: Generator<String>
  val timeZone: Generator<String> // TODO Enum?
  val city: Generator<String>
  val streetName: Generator<String>
  val streetAddress: Generator<String>
  val fullAddress: Generator<String>
  val mailbox: Generator<String>
}
