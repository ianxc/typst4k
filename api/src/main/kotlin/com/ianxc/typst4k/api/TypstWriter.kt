package com.ianxc.typst4k.api

interface TypstWriter {
    @Throws(TypstWriteException::class)
    fun write(request: TypstWriteRequest, execOptions: TypstExecutionOptions): TypstWriteResponse
}
