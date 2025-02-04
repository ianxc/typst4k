package com.ianxc.typst4k.api

abstract class TypstWriteException
@JvmOverloads
constructor(message: String? = null, cause: Exception? = null) : RuntimeException(message, cause)
