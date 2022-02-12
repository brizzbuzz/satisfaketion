package io.github.unredundant.satisfaketion.generators.common

import co.touchlab.kermit.Logger
import okio.FileSystem
import okio.Path
import okio.buffer

actual object Reader {
  private val logger = Logger.withTag("JvmFileReader")
  actual fun read(path: Path): String {
    val canonicalPath = FileSystem.SYSTEM.canonicalize(path)
    logger.i { "Reading File: $canonicalPath" }
    val builder = StringBuilder()
    FileSystem.SYSTEM.source(path).use { fs ->
      fs.buffer().use { bfs ->
        var currLine = bfs.readUtf8Line()
        while (currLine != null) {
          builder.appendLine(currLine)
          currLine = bfs.readUtf8Line()
        }
      }
    }
    return builder.toString().trim()
  }
}
