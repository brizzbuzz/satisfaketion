package io.github.unredundant.satisfaketion.core

import kotlin.random.Random
import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.KProperty1
import kotlin.reflect.full.primaryConstructor

class Faker<T : Any>(private val clazz: KClass<T>) {

  private var propertyMap: Map<KParameter, Generator<*>> = emptyMap()

  companion object {
    inline operator fun <reified T : Any> invoke(init: Faker<T>.() -> Unit): Faker<T> {
      val builder = Faker(T::class)
      return builder.apply(init)
    }
  }

  fun generate(seed: Random = Random.Default): T {
    val constructor = clazz.primaryConstructor ?: error("$clazz does not have a primary constructor, cannot generate")
    val generatedParams = propertyMap.mapValues { (_, v) -> v.generate(seed) }
    return constructor.callBy(generatedParams)
  }

  operator fun <R> KProperty1<T, R>.invoke(init: (KProperty1<T, R>) -> Generator<R>) {
    val param = clazz.primaryConstructor?.parameters?.find { it.name == name }
      ?: error("Unable to match $name to a parameter for $clazz")
    require(!propertyMap.containsKey(param)) { "${param.name} has already been registered" }
    val generator = init.invoke(this)
    propertyMap = propertyMap.plus(param to generator)
  }
}
