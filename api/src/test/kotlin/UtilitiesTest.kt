package com.ianxc.typst4k.utils

import com.ianxc.tpyst4k.utils.Printer
import kotlin.test.Test
import kotlin.test.assertEquals

internal class PrinterTest {
    @Test
    fun testMessage() {
        val message = "message"
        val testPrinter = Printer(message)
        assertEquals(testPrinter.message, message)
    }
}
