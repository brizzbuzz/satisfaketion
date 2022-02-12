package io.github.unredundant.satisfaketion.generators.common

import co.touchlab.kermit.Logger
import okio.NodeJsFileSystem
import okio.Path
import okio.buffer
import okio.use

actual object Reader {
  private val logger = Logger.withTag("JsFileReader")
  actual fun read(path: Path): String {
    NodeJsFileSystem.source(path)
    val canonicalPath = NodeJsFileSystem.canonicalize(path)
    logger.i { "Reading File: $canonicalPath" }
    val builder = StringBuilder()
    NodeJsFileSystem.source(path).use { fs ->
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
