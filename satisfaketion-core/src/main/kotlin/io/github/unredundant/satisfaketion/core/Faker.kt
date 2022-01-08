package io.github.unredundant.satisfaketion.core

import kotlin.random.Random
import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.KProperty1
import kotlin.reflect.full.primaryConstructor

class Faker<T : Any>(private val clazz: KClass<T>) {

  private var propertyMap: Map<KParameter, Generator<*>> = emptyMap()
  private var cache: MutableMap<KParameter, Any?> = mutableMapOf()

  companion object {
    inline operator fun <reified T : Any> invoke(init: Faker<T>.() -> Unit): Faker<T> {
      val builder = Faker(T::class)
      return builder.apply(init)
    }
  }

  fun generate(seed: Random = Random.Default): T {
    val constructor = clazz.primaryConstructor ?: error("$clazz does not have a primary constructor, cannot generate")
    val generatedParams = propertyMap.mapValues { (k, v) ->
      if (cache.containsKey(k)) {
        cache[k]
      } else {
        val value = v.generate(seed)
        cache[k] = value
        value
      }
    }
    cache = mutableMapOf()
    return constructor.callBy(generatedParams)
  }

  operator fun <R> KProperty1<T, R>.invoke(init: (KProperty1<T, R>) -> Generator<R>) {
    val param = clazz.primaryConstructor?.parameters?.find { it.name == name }
      ?: error("Unable to match $name to a parameter for $clazz")
    require(!propertyMap.containsKey(param)) { "${param.name} has already been registered" }
    val generator = init.invoke(this)
    propertyMap = propertyMap.plus(param to generator)
  }

  inner class CorrelatedPropertyGenerator<R, RR>(
    private val prop: KProperty1<T, R>,
    val invoke: (R, Random) -> RR,
  ) : Generator<RR> {
    override fun generate(seed: Random): RR {
      val param = clazz.primaryConstructor?.parameters?.find { it.name == prop.name }
        ?: error("Unable to match ${prop.name} to a parameter for $clazz")
      val input = if (cache.containsKey(param)) {
        cache[param]
      } else {
        val value = propertyMap[param]!!.generate(seed)
        cache[param] = value
        value
      }
      return invoke(input as R, seed)
    }
  }
}
