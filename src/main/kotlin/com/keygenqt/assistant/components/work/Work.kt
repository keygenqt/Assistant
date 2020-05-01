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
import com.keygenqt.assistant.utils.*
import java.io.*
import java.util.*
import java.util.regex.*

class Work(private val dir: String, private val search: String, private val sort: String, private val exclude: String,
    private val lines: Int) {

    private val wEx: WorkExtension = WorkExtension()
    private val wRe: WorkRename = WorkRename()
    private val wStat: WorkStatistic = WorkStatistic()
    private val wLines: WorkLines = WorkLines()
    private val wSize: WorkSize = WorkSize()

    fun size() {
        Info.showInfo(wSize.getPreview(getFiles(dir, search, sort, exclude, lines)))
        exit()
    }

    fun countLines(lineRegex: String) {
        Info.showInfo(wLines.getPreview(getFiles(dir, search, sort, exclude, lines), lineRegex))
        exit()
    }

    fun statistic() {
        Info.showInfo(wStat.getPreview(getFiles(dir, search, sort, exclude, lines)))
        exit()
    }

    fun extensionUp() {
        showInfo(wEx.getPreviewUp(getFiles(dir, search, sort, exclude, lines))) {
            val errors = wEx.update()
            if (errors.isNotEmpty()) {
                Info.showErrorSave(errors)
            } else {
                Info.showSuccessSave()
            }
        }
    }

    fun extensionLower() {
        showInfo(wEx.getPreviewLower(getFiles(dir, search, sort, exclude, lines))) {
            val errors = wEx.update()
            if (errors.isNotEmpty()) {
                Info.showErrorSave(errors)
            } else {
                Info.showSuccessSave()
            }
        }
    }

    fun rename(template: String, zeros: String) {
        showInfo(wRe.getPreview(getFiles(dir, search, sort, exclude, lines), template, zeros)) {
            val errors = wRe.update()
            if (errors.isNotEmpty()) {
                Info.showErrorSave(errors)
            } else {
                Info.showSuccessSave()
            }
        }
    }

    private fun getFiles(dir: String, search: String, sort: String, exclude: String, readLines: Int): Array<SortFile> {
        val files = File(dir).walk()
            .filter { it.isFile }
            .filter { it.name.matches(search.toRegex()) }
            .filter {
                var filter = true
                if (exclude.isNotEmpty()) {
                    val reader = BufferedReader(FileReader(it))
                    var line = reader.readLine()
                    var index = 1
                    while (line != null) {
                        if (index > readLines) {
                            break
                        }
                        if (Pattern.compile(exclude).matcher(line).find()) {
                            filter = false
                            break
                        }
                        index++
                        line = reader.readLine()
                    }
                    reader.close()
                }
                filter
            }
            .map { SortFile(it.absolutePath) }.toList().toTypedArray()

        when (sort) {
            TYPE_SORT_LAST_MOD -> Arrays.sort(files) { f1, f2 -> f1.lastModified().compareTo(f2.lastModified()) }
            TYPE_SORT_NATURAL -> Arrays.sort(files)
            else -> {
                Arrays.sort(files) { f1, f2 -> f1.name.compareTo(f2.name, true) }
            }
        }

        if (files.isEmpty()) {
            Info.notFoundFiles();
        }

        return files
    }

    private fun showInfo(files: LinkedHashMap<String, SortFile>, listener: () -> Unit) {
        for ((key, value) in files) {
            println(String.format("%-40s -> %s", value.name, key))
        }

        val br = BufferedReader(InputStreamReader(System.`in`))
        print("\nReady? Update files (yes/no):")

        when (br.readLine()) {
            "yes" -> {
                listener.invoke()
            }
            "no" -> {
                Info.showClose()
            }
            else -> {
                Info.showErrorYesNo()
            }
        }
    }
}