import org.apache.fontbox.ttf.NameRecord
import org.apache.fontbox.ttf.NamingTable
import org.apache.fontbox.ttf.OTFParser
import org.apache.fontbox.ttf.TTFParser
import java.io.File

class FontMetadata {
    public fun getFontName(filePath: String?): String? {
        val file = File(filePath ?: "")
        try {
            val ttfParser = TTFParser()
            val font = ttfParser.parse(file)
            val naming = font.naming
            var fontFamily = naming.fontFamily
            if (fontFamily.isNullOrEmpty()) {
                fontFamily = getFontFamily(naming)
            }
            font.close()
            return fontFamily
        } catch (e: Exception) {
            e.printStackTrace()
        }

        try {
            val otfParser = OTFParser()
            val font = otfParser.parse(filePath)
            val naming = font.naming
            var fontFamily = naming.fontFamily
            if (fontFamily.isNullOrEmpty()) {
                fontFamily = getFontFamily(naming)
            }
            font.close()
            return fontFamily
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    private fun getFontFamily(namingTable: NamingTable): String {
        for (nameRecord in namingTable.nameRecords) {
            if (nameRecord.nameId == NameRecord.NAME_FONT_FAMILY_NAME) {
                if (!nameRecord.string.isNullOrEmpty()) {
                    return nameRecord.string
                }
            }
        }
        return ""
    }
}