package io.github.unredundant.satisfaketion.core

import kotlin.random.Random
import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.KProperty1
import kotlin.reflect.full.primaryConstructor

actual class Faker<T : Any>(private val clazz: KClass<T>) {
  private var propertyMap: Map<KParameter, Generator<*>> = emptyMap()
  private var cache: MutableMap<KParameter, Any?> = mutableMapOf()

  companion object {
    /**
     * Allows for streamlined [Faker] invocation, leveraging a reified [T] to instantiate the [Faker]
     */
    inline operator fun <reified T : Any> invoke(init: Faker<T>.() -> Unit): Faker<T> {
      val builder = Faker(T::class)
      return builder.apply(init)
    }
  }

  actual fun generate(): T = generate(Random.Default)

  /**
   * Generates an instance of [T] using the [Generator] instances registered via [Faker.invoke]
   * @param seed [Random] to be passed to each [Generator] in order to enable deterministic generation
   * @return Instance of [T] containing all registered generated values
   */
  actual fun generate(seed: Random): T {
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

  /**
   * Binds a [KProperty1] to the specified [Generator].  Stored by the [Faker] to
   * be used in each invocation of [Faker.generate].
   * @param init Function parameter that is invoked in order to register the provided [Generator]
   */
  operator fun <R> KProperty1<T, R>.invoke(init: (KProperty1<T, R>) -> Generator<R>) {
    val param = clazz.primaryConstructor?.parameters?.find { it.name == name }
      ?: error("Unable to match $name to a parameter for $clazz")
    require(!propertyMap.containsKey(param)) { "${param.name} has already been registered" }
    val generator = init.invoke(this)
    propertyMap = propertyMap.plus(param to generator)
  }

  inner class CorrelatedPropertyGenerator<R, RR> : Generator<RR> {

    private var prop: KProperty1<T, R>
    private var invoke: (R, Random) -> RR

    /**
     * This constructor enables users to nest a [CorrelatedPropertyGenerator].
     * Doing so results in a correlation chain by
     * which all required values are generated ahead of the correlated [Generator].
     * @param prop [KProperty1] with which to correlate this [Generator]
     * @param invoke Function parameter providing access to the nested [Generator]
     */
    constructor(
      prop: KProperty1<T, R>,
      invoke: (R) -> Generator<RR>
    ) {
      this.prop = prop
      this.invoke = { v, seed ->
        invoke(v).generate(seed)
      }
    }

    /**
     * This constructor enables direct correlation with a desired [Generator]
     * @param prop [KProperty1] with which to correlate this [Generator]
     * @param invoke Function parameter used to provide the correlated value for use in the underlying [Generator]
     */
    constructor(
      prop: KProperty1<T, R>,
      invoke: (R, Random) -> RR,
    ) {
      this.prop = prop
      this.invoke = invoke
    }

    /**
     * Implementation of the [Generator] interface, allowing for the correlated generator to be invoked on the fly once
     * provided with the requisite data.
     * @param seed Will be provided by correlated [Generator]
     */
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
