package com.ianxc.tpyst4k.processwriter

import com.ianxc.typst4k.api.TypstExecutionOptions
import com.ianxc.typst4k.api.TypstProcessWriteException
import com.ianxc.typst4k.api.TypstWriteRequest
import com.ianxc.typst4k.api.TypstWriteResponse
import com.ianxc.typst4k.api.TypstWriter
import java.io.File
import java.util.concurrent.TimeUnit
import kotlin.io.path.absolutePathString
import kotlin.time.TimeSource

class TypstProcessWriter(
    private val typstExePath: String,
    private val timeSource: TimeSource.WithComparableMarks,
) : TypstWriter {

    override fun write(
        request: TypstWriteRequest,
        executionOptions: TypstExecutionOptions,
    ): TypstWriteResponse {
        val cmd = requestToCmd(request)
        val builder = ProcessBuilder(cmd)

        val markStart = timeSource.markNow()
        val process = builder.start()

        val (n, unit) = extractTimeout(executionOptions)
        val done = process.waitFor(n, unit)
        val markEnd = timeSource.markNow()
        if (!done) {
            process.destroyForcibly()
            process.waitFor()
            throw TypstProcessWriteException(process.exitValue())
        }

        val processDuration = markEnd - markStart
        return TypstWriteResponse(processDuration)
    }

    internal fun requestToCmd(request: TypstWriteRequest): List<String> {
        val cmd = arrayListOf(typstExePath, "compile")
        request.root?.let {
            cmd.add("--root")
            cmd.add(it.root.absolutePathString())
        }
        request.kvInputs.forEach { (key, value) ->
            cmd.add("--input")
            cmd.add("$key=$value")
        }
        if (request.fontPaths.isNotEmpty()) {
            cmd.add("--font-path")
            val joined = request.fontPaths.joinToString(File.pathSeparator)
            cmd.add(joined)
        }
        if (request.ignoreSystemFonts == true) {
            cmd.add("--ignore-system-fonts")
        }
        request.creationTimestamp?.let {
            cmd.add("--creation-timestamp")
            cmd.add(it.epochSecond.toString())
        }
        request.diagnosticFormat?.let {
            cmd.add("--diagnostic-format")
            cmd.add(it.v)
        }
        request.jobs?.let {
            cmd.add("--jobs")
            cmd.add(it.toString())
        }
        request.format?.let {
            cmd.add("--format")
            cmd.add(it.v)
        }
        request.ppi?.let {
            cmd.add("--ppi")
            cmd.add(it.toString())
        }
        if (request.pdfStandards.isNotEmpty()) {
            cmd.add("--pdf-standard")
            val joined = request.pdfStandards.joinToString(",") { it.v }
            cmd.add(joined)
        }

        return cmd
    }

    internal fun extractTimeout(executionOptions: TypstExecutionOptions): Pair<Long, TimeUnit> {
        return when (val t = executionOptions.timeout) {
            null -> 1L to TimeUnit.MINUTES
            else -> {
                val seconds = t.inWholeSeconds.coerceAtLeast(1L)
                seconds to TimeUnit.SECONDS
            }
        }
    }
}
