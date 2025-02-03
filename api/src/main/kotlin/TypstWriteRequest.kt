package com.ianxc.tpyst4k.utils

import java.nio.file.Path
import java.time.Instant

data class TypstWriteRequest(
    val inputPath: Path,
    val outputPath: Path,
    val rootDir: Path? = null,
    val kvInputs: Map<String, String> = emptyMap(),
    val fontPaths: List<Path> = emptyList(),
    val ignoreSystemFonts: Boolean? = null,
    val creationTimestamp: Instant? = null,
    val jobs: Int? = null,
    val format: TypstFormat? = null,
    val ppi: Int? = null,
    val pdfStandards: List<TypstPdfStandard> = emptyList(),
)

enum class TypstDiagnosticFormat(val v: String) {
    HUMAN("human"),
    SHORT("short"),
}

enum class TypstFormat(val v: String) {
    PDF("pdf"),
    PNG("png"),
    SVG("svg"),
}

enum class TypstPdfStandard(val v: String) {
    V_1_7("1.7"),
    A_2B("a-2b"),
}
