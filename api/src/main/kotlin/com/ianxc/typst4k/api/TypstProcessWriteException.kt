package com.ianxc.typst4k.api

class TypstProcessWriteException(exitCode: Int) :
    TypstWriteException("Failed to write Typst template via process impl: exitCode=$exitCode")
