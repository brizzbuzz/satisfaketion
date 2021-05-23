package io.github.rgbrizzlehizzle.satisfaketion.generators.en

import io.github.rgbrizzlehizzle.satisfaketion.core.Generator
import io.github.rgbrizzlehizzle.satisfaketion.core.letterify
import io.github.rgbrizzlehizzle.satisfaketion.core.numerify
import kotlin.random.Random

class Barcode(private val random: Random = Random.Default) {
  val ean8 = Generator { "#######".numerify(random) }

  val ean13 = Generator { "############".numerify(random) }

  val upcA = Generator { "###########".numerify(random) }

  val upcE = Generator {
    val format = upcEs[random.nextInt(upcEs.size)]
    format.numerify(random)
  }

  val compositeSymbol = Generator {
    val format = compositeSymbols[random.nextInt(compositeSymbols.size)]
    format.numerify(random).letterify(random)
  }

  val isbn = Generator {
    val format = isbns[random.nextInt(isbns.size)]
    format.numerify(random)
  }

  val ismn = Generator { "9790########".numerify(random) }

  val issn = Generator { "977#########".numerify(random) }

  private val upcEs = listOf("0######", "1######")
  private val compositeSymbols = listOf(
    "########",
    "????????",
    "####????",
    "????####",
    "##??##??",
    "??##??##"
  )
  private val isbns = listOf(
    "978#########",
    "9798########",
    "97910#######",
    "97911#######",
    "97912#######"
  )
}
