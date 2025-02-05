package com.ianxc.typst4k.processwriter

import java.nio.file.Path
import kotlin.io.path.toPath

inline fun <reified T: Any> T.resourceOf(location: String): Path? {
    return this.javaClass.getResource(location)?.toURI()?.toPath()
}
