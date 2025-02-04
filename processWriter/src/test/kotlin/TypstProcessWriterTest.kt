package com.ianxc.tpyst4k.processwriter

import com.ianxc.tpyst4k.utils.TypstPdfStandard
import com.ianxc.tpyst4k.utils.TypstWriteRequest
import io.kotest.matchers.shouldBe
import java.time.Instant
import kotlin.io.path.Path
import kotlin.time.TimeSource
import org.junit.jupiter.api.Test

class TypstProcessWriterTest {
    private val sut = TypstProcessWriter("typst", TimeSource.Monotonic)

    @Test
    fun `should make cmd list`() {
        val request =
            TypstWriteRequest(
                Path("r1_input.typ"),
                Path("r1_output.pdf"),
                kvInputs = mapOf("overview" to "overview.json", "stream" to "stream.json"),
                creationTimestamp = Instant.ofEpochMilli(1738675450),
                jobs = 2,
                pdfStandards = listOf(TypstPdfStandard.A_2B),
            )

        val cmd = sut.requestToCmd(request)

        cmd shouldBe
            listOf(
                "typst",
                "compile",
                "--input",
                "overview=overview.json",
                "--input",
                "stream=stream.json",
                "--creation-timestamp",
                "1738675",
                "--jobs",
                "2",
                "--pdf-standard",
                "a-2b",
            )
    }
}
