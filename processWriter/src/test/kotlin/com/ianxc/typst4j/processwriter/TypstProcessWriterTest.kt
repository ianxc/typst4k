package com.ianxc.typst4j.processwriter

import com.ianxc.tpyst4k.processwriter.TypstProcessWriter
import com.ianxc.typst4k.api.TypstPdfStandard
import com.ianxc.typst4k.api.TypstWriteRequest
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
                Path("/templates/r1_input.typ"),
                Path("/templates/r1_output.pdf"),
                kvInputs =
                    mapOf(
                        "overview" to "/tmp/req-1/overview.json",
                        "stream" to "/tmp/req-1/stream.json"),
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
                "overview=/tmp/req-1/overview.json",
                "--input",
                "stream=/tmp/req-1/stream.json",
                "--creation-timestamp",
                "1738675",
                "--jobs",
                "2",
                "--pdf-standard",
                "a-2b",
                "/templates/r1_input.typ",
                "/templates/r1_output.pdf")
    }
}
