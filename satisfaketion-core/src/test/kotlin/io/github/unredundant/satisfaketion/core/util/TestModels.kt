package io.github.unredundant.satisfaketion.core.util

import kotlinx.datetime.LocalDateTime

data class SimpleDataClass(val a: String, val b: Int)
data class AnotherSimpleClass(val c: Boolean, val d: String = "hey dude")
data class TimingStuff(val start: LocalDateTime, val end: LocalDateTime, val middle: LocalDateTime)
