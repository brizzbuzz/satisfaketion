package io.github.unredundant.satisfaketion.generators.common

import okio.Path

expect object Reader {
  fun read(path: Path): String
}
