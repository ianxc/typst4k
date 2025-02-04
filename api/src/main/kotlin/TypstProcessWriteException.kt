package com.ianxc.tpyst4k.utils

class TypstProcessWriteException(exitCode: Int) :
    TypstWriteException("Failed to write Typst template via process impl: exitCode=$exitCode")
