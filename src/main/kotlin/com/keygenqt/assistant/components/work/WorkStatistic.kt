/*
 * Copyright 2020 Vitaliy Zarubin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.keygenqt.assistant.components.work

import com.keygenqt.assistant.components.*
import java.io.*
import java.text.*
import java.util.*
import kotlin.math.*

class WorkStatistic() {

    fun getPreview(files: Array<SortFile>): String {
        var lines = 0
        var older = 0L
        var olderName = ""
        var newest = 0L
        var newestName = ""
        var max = 0
        var maxName = ""
        var min = 0
        var minName = ""
        val extension = arrayListOf<String>()
        var maxSize = 0L
        var maxSizeName = ""
        var minSize = 0L
        var minSizeName = ""
        for (file in files) {
            var line = 0
            val mod = file.lastModified()
            if (!extension.contains(file.extension)) {
                extension.add(file.extension)
            }
            if (older > mod || older == 0L) {
                olderName = file.name
                older = mod
            }
            if (newest < mod || newest == 0L) {
                newestName = file.name
                newest = mod
            }
            val reader = BufferedReader(FileReader(file))
            while (reader.readLine() !== null) line++
            reader.close()

            if (max <= line) {
                max = line
                maxName = file.name
            }

            if (min > line || min == 0) {
                min = line
                minName = file.name
            }

            if (maxSize <= file.length()) {
                maxSize = file.length()
                maxSizeName = file.name
            }

            if (minSize > file.length() || minSize == 0L) {
                minSize = file.length()
                minSizeName = file.name
            }

            lines += line
        }
        return """
            count files:        ${files.size}
            count lines:        $lines
            count extensions:   ${extension.size}
            
            max size:           ${String.format("%-20s(%s)", getNaturalSize(maxSize), maxSizeName)}
            min size:           ${String.format("%-20s(%s)", getNaturalSize(minSize), minSizeName)}
            
            max lines:          ${String.format("%-20s(%s)", max, maxName)}
            min lines:          ${String.format("%-20s(%s)", min, minName)}
            
            modified last:      ${SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(Date(newest))} ($newestName)
            modified oldest:    ${SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(Date(older))} ($olderName)
        """.trimIndent()
    }

    private fun getNaturalSize(size: Long): String {
        val absB = if (size == Long.MIN_VALUE) Long.MAX_VALUE else abs(size)
        if (absB < 1024) {
            return "$size B"
        }
        var value = absB
        val ci: CharacterIterator = StringCharacterIterator("KMGTPE")
        var i = 40
        while (i >= 0 && absB > 0xfffccccccccccccL shr i) {
            value = value shr 10
            ci.next()
            i -= 10
        }
        value *= java.lang.Long.signum(size).toLong()
        return String.format("%.1f %ciB", value / 1024.0, ci.current())
    }
}