package dev.lynne.mc_controls.banner

import com.github.lalyos.jfiglet.FigletFont
import dev.lynne.mc_controls.banner.data.BannerDataBlock
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class Banner(
    var commonWidth: Int = 50,
    var delimiterChar: Char = '=',
    var title: String? = null,
    var description: String? = null,
    val data: MutableList<BannerDataBlock> = mutableListOf()
) {
    fun render(): String {
        val baos = ByteArrayOutputStream()
        PrintStream(baos).use { ps -> this.print(ps) }
        return baos.toString(Charsets.UTF_8)
    }
    fun print(out: PrintStream) {
        val title = title
        val description = description

        if (title != null)
            out.print(FigletFont.convertOneLine(title))
        if (description != null)
            out.print(description)

        val data = data
        for (dataBlock in data) {
            out.append("\n")
            out.appendLine(delimiterChar.toString().repeat(commonWidth))
            out.append(dataBlock.build(commonWidth))
        }
    }
}