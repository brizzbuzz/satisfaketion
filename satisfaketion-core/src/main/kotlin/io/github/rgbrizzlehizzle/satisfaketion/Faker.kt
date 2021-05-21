package io.github.rgbrizzlehizzle.satisfaketion

import kotlin.reflect.KProperty1
import org.slf4j.LoggerFactory

class Faker<T> {

  var propertyMap: Map<String, Generator<*>> = emptyMap()

  companion object {
    operator fun <T> invoke(init: Faker<T>.() -> Unit): Faker<T> {
      val builder = Faker<T>()
      return builder.apply(init)
    }
  }

  private val logger = LoggerFactory.getLogger(javaClass)

  operator fun <R> KProperty1<T, R>.invoke(init: (r: R?) -> Generator<R>) {
    logger.debug("Look ma I'm in a property 🤠")
    require(!propertyMap.containsKey(this.name)) { "${this.name} has already been registered" }
    val generator = init.invoke(null) // TODO WHY THE F$*# DO I NEED THIS GENERIC PARAM / RECEIVER
    propertyMap = propertyMap.plus(this.name to generator)
  }
}
