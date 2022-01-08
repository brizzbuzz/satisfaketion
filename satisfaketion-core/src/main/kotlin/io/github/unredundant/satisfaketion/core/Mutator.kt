package io.github.unredundant.satisfaketion.core

fun interface Mutator<R, RR> {
  fun mutate(generator: Generator<R>): Generator<RR>
}
