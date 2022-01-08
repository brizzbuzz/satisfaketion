package io.github.rgbrizzlehizzle.satisfaketion.generators.en

import io.github.rgbrizzlehizzle.satisfaketion.core.Extensions.letterify
import io.github.rgbrizzlehizzle.satisfaketion.core.Extensions.numerify
import io.github.rgbrizzlehizzle.satisfaketion.core.Generator

object Barcode {
  val ean8 = Generator { r -> "#######".numerify(r) }

  val ean13 = Generator { r -> "############".numerify(r) }

  val upcA = Generator { r -> "###########".numerify(r) }

  val upcE = Generator { r ->
    val format = upcEs[r.nextInt(upcEs.size)]
    format.numerify(r)
  }

  val compositeSymbol = Generator { r ->
    val format = compositeSymbols[r.nextInt(compositeSymbols.size)]
    format.numerify(r).letterify(r)
  }

  val isbn = Generator { r ->
    val format = isbns[r.nextInt(isbns.size)]
    format.numerify(r)
  }

  val ismn = Generator { r -> "9790########".numerify(r) }

  val issn = Generator { r -> "977#########".numerify(r) }

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
