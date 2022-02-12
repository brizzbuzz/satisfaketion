package io.github.unredundant.satisfaketion.generators.common

import io.github.unredundant.satisfaketion.core.Generator

interface Name {
  val maleFirstName: Generator<String>
  val femaleFirstName: Generator<String>
  val neutralFirstName: Generator<String>
  val firstName: Generator<String>
  val lastName: Generator<String>
  val prefix: Generator<String>
  val suffix: Generator<String>
}
