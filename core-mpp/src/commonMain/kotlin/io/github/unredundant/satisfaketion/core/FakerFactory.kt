package io.github.unredundant.satisfaketion.core

expect object FakerFactory {
  fun <T> createFaker(): Faker<T>
}
