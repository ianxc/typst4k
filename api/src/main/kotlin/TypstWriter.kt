package com.ianxc.tpyst4k.utils

interface TypstWriter {
    @Throws(TypstWriteException::class) fun write(request: TypstWriteRequest)
}
