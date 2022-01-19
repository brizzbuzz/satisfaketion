package io.github.unredundant.satisfaketion.core

import kotlin.random.Random

/**
 * Collection of handy extensions used throughout the library.
 */
object Extensions {
  private object Alphabet {
    const val source = "abcdefghijklmnopqrstuvwxyz"
  }

  /**
   * Replaces every `#` char for this [String] receiver with a random int from 0 to 9 inclusive
   * and returns the modified [String].
   * @param random Seed from which to generate all random values.  Defaults to [Random.Default]
   * @receiver [String]
   * @return "Number-ified" value
   */
  @Suppress("MagicNumber")
  fun String.numerify(random: Random = Random.Default): String {
    return map { if (it == '#') random.nextInt(10).toString() else "$it" }
      .joinToString("")
  }

  /**
   * Replaces every `?` char for this [String] receiver with a random upper-case letter from the English alphabet
   * and returns the modified [String].
   * @param random Seed from which to generate all random values.  Defaults to [Random.Default]
   * @param upper flag that will convert all replaced characters to their uppercase representation.  Defaults to true
   * @return "Letter-ified" value
   */
  fun String.letterify(random: Random = Random.Default, upper: Boolean = true): String {
    return map { if (it == '?') random.nextLetter(upper = upper).toString() else "$it" }
      .joinToString("")
  }

  /**
   * Returns a random letter from [Alphabet.source]
   * @param upper flag that will convert all replaced characters to their uppercase representation.  Defaults to true
   * @return generated [Char]
   */
  fun Random.nextLetter(upper: Boolean): Char {
    val source = if (upper) Alphabet.source.uppercase() else Alphabet.source
    return source[nextInt(source.length)]
  }

  /**
   * Returns a random element of a list
   * @receiver List from which to pick item from
   * @param random Seed from which to generate all random values.  Defaults to [Random.Default]
   * @return Single element [T] from receiver
   */
  fun <T> List<T>.nextItem(random: Random = Random.Default): T = get(random.nextInt(size))

  /**
   * Allows for a [Mutator] to be applied directly to a given [Generator]
   * @receiver [Generator] of type [T]
   * @param mut [Mutator] that takes the receiver and returns a [Generator] of type [TT]
   * @return [Generator] of type [TT]
   */
  fun <T, TT> Generator<T>.mutate(mut: Mutator<T, TT>): Generator<TT> {
    return mut.mutate(this)
  }
}
