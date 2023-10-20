package com.folioreader.util

import android.content.Context
import android.content.res.AssetManager
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 * @author Tyler Sedlar
 */
object FontFinder {

    private var sysFonts: Map<String, File>? = null

    @JvmStatic
    fun getSystemFonts(): Map<String, File> {
        if (sysFonts != null) {
            return sysFonts!!
        }

        val fonts = HashMap<String, File>()

        val sysFontDir = File("/system/fonts/")
        val fontSuffix = ".ttf"

        // Collect system fonts
        for (fontFile in sysFontDir.listFiles()) {
            val fontName: String = fontFile.name
            if (fontName.endsWith(fontSuffix)) {
                val key = fontName.subSequence(0, fontName.lastIndexOf(fontSuffix)).toString()
                fonts[key] = fontFile
            }
        }

        sysFonts = fonts

        return fonts
    }

    @JvmStatic
    fun getUserFonts(): Map<String, File> {
        val fonts = HashMap<String, File>()

        val fontDirs = arrayOf(
            File(Environment.getExternalStorageDirectory(), "fonts/")
        )
        val fontSuffix = ".ttf"

        fontDirs.forEach { fontDir ->
            // Collect user fonts
            if (fontDir.exists() && fontDir.isDirectory) {
                fontDir.walkTopDown()
                    .filter { f -> f.name.endsWith(fontSuffix) }
                    .forEach { fontFile ->
                        val fontName = fontFile.name
                        val key =
                            fontName.subSequence(0, fontName.lastIndexOf(fontSuffix)).toString()
                        fonts[key] = fontFile
                    }
            }
        }
        println("--------------USER FONTS: -----------------------")
        println(fonts)
        println("------------------ DONE USER FONTS --------------------")
        return fonts
    }

    @Throws(IOException::class)
    private fun copyAssetToFile(assetManager: AssetManager, assetPath: String, destinationFile: File) {
        val inputStream = assetManager.open(assetPath)
        val outputStream = FileOutputStream(destinationFile)

        inputStream.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }
    }

    @JvmStatic
    fun getFontFile(key: String, context: Context): File? {
        val system = getSystemFonts()
        val user = getUserFonts()

        return when {
            system.containsKey(key) -> system[key]
            user.containsKey(key) -> user[key]
            else -> null
        }
    }

    @JvmStatic
    fun isSystemFont(key: String): Boolean {
        return getSystemFonts().containsKey(key)
    }
}