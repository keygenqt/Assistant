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

class WorkLines {

    fun getPreview(files: Array<SortFile>, lineRegex: String): String {
        val lines = hashMapOf<SortFile, ArrayList<String>>()
        var count = 0
        for (file in files) {

            val reader = BufferedReader(FileReader(file))
            var index = 1
            var line = reader.readLine()
            while (reader.readLine() !== null) {
                if (line.matches(lineRegex.toRegex())) {
                    if (!lines.containsKey(file)) {
                        lines[file] = arrayListOf()
                    }
                    lines[file]?.add("$index::${line.trim()}")
                    count++
                }
                index++
                line = reader.readLine()
            }
            reader.close()
        }
        var result = ""
        for ((file, item) in lines) {
            result += "\n\n------------\n${file.name} (${file.absoluteFile})"
            result += "\n${item.joinToString("\n")}"
        }

        return """|
                  |$result
                  |
                  |------------
                  |count lines: $count
        """.trimMargin()
    }
}