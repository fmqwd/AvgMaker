package com.fmqwd.avgmaker.utils

import java.io.File
import java.util.zip.ZipFile

class ZipUtils {

    interface OnProgressListener {
        fun onProgress(progress: Int)
    }

    fun unzip(zipFilePath: String, destinationPath: String, onProgressListener: OnProgressListener?) {
        ZipFile(zipFilePath).use { zipFile ->
            val totalSize = zipFile.entries().asSequence().sumOf { it.size.toInt() }
            var extractedSize = 0

            zipFile.entries().asSequence().forEach { zipEntry ->
                val file = File(destinationPath, zipEntry.name)
                if (zipEntry.isDirectory) {
                    file.mkdirs()
                } else {
                    file.parentFile?.mkdirs()
                    zipFile.getInputStream(zipEntry).use { inputStream ->
                        file.outputStream().use { outputStream ->
                            inputStream.copyTo(outputStream)
                        }
                    }
                    extractedSize += zipEntry.size.toInt()
                    val progress = (extractedSize * 100 / totalSize)
                    onProgressListener?.onProgress(progress)
                }
            }
        }
    }
}