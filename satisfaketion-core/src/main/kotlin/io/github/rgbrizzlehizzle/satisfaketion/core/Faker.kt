package io.github.rgbrizzlehizzle.satisfaketion.core

import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.KProperty1
import kotlin.reflect.full.primaryConstructor
import org.slf4j.LoggerFactory

class Faker<T : Any>(private val clazz: KClass<T>) {

  private val logger = LoggerFactory.getLogger(javaClass)
  private var propertyMap: Map<KParameter, Generator<*>> = emptyMap()

  companion object {
    inline operator fun <reified T : Any> invoke(init: Faker<T>.() -> Unit): Faker<T> {
      val builder = Faker(T::class)
      return builder.apply(init)
    }
  }

  fun generate(): T {
    val constructor = clazz.primaryConstructor ?: error("$clazz does not have a primary constructor, cannot generate")
    val generatedParams = propertyMap.mapValues { (_, v) -> v.generate() }
    return constructor.callBy(generatedParams)
  }

  operator fun <R> KProperty1<T, R>.invoke(init: (r: R?) -> Generator<R>) {
    logger.debug("Look ma I'm in a property 🤠")
    val param = clazz.primaryConstructor?.parameters?.find { it.name == name }
      ?: error("Unable to match $name to a parameter for $clazz")
    require(!propertyMap.containsKey(param)) { "${param.name} has already been registered" }
    val generator = init.invoke(null) // TODO WHY THE F$*# DO I NEED THIS GENERIC PARAM / RECEIVER
    propertyMap = propertyMap.plus(param to generator)
  }
}
