package io.github.rgbrizzlehizzle.satisfaketion.generators.common

import io.github.rgbrizzlehizzle.satisfaketion.core.Generator

interface Name {
  val maleFirstName: Generator<String>
  val femaleFirstName: Generator<String>
  val neutralFirstName: Generator<String>
  val firstName: Generator<String>
  val lastName: Generator<String>
  val prefix: Generator<String>
  val suffix: Generator<String>
}
