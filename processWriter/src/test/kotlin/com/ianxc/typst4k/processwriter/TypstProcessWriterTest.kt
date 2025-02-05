package com.ianxc.typst4k.processwriter

import com.ianxc.tpyst4k.processwriter.TypstProcessWriter
import com.ianxc.typst4k.api.TypstExecutionOptions
import com.ianxc.typst4k.api.TypstPdfStandard
import com.ianxc.typst4k.api.TypstWriteRequest
import io.kotest.matchers.comparables.shouldBeLessThan
import io.kotest.matchers.shouldBe
import java.time.Instant
import kotlin.io.path.Path
import kotlin.io.path.createParentDirectories
import kotlin.io.path.pathString
import kotlin.time.Duration.Companion.seconds
import kotlin.time.TimeSource
import org.junit.jupiter.api.Test

internal class TypstProcessWriterTest {
    private val sut = TypstProcessWriter("typst", TimeSource.Monotonic)

    @Test
    fun `should generate file`() {
        val dataRootPath = resourceOf("/requests/test_1")!!
        val request =
            TypstWriteRequest(
                resourceOf("/templates/color_table/main.typ")!!,
                Path("build/tmp/test_1/r1_output.pdf").createParentDirectories(),
                root = dataRootPath,
                kvInputs = mapOf("data_file" to dataRootPath.resolve("source.csv").pathString),
                pdfStandards = listOf(TypstPdfStandard.V_1_7))
        val opts = TypstExecutionOptions(10.seconds)

        val response = sut.write(request, opts)

        response.writeDuration shouldBeLessThan 30.seconds
        println(response)
    }

    @Test
    fun `should make cmd list`() {
        val request =
            TypstWriteRequest(
                Path("/templates/r1_input.typ"),
                Path("/tmp/req-1/r1_output.pdf"),
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
                "/tmp/req-1/r1_output.pdf")
    }
}
