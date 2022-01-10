package io.github.unredundant.satisfaketion.generators.common

object Utils {
  fun getFile(fileName: String): String {
    return this::class.java.classLoader.getResource(fileName).readText()
  }
}
