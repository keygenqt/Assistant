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

class Work(private val dir: String) {

    private val ex: WorkExtension = WorkExtension()
    private val re: WorkRename = WorkRename()

    fun extensionUp(search: String, sort: String) {
        showInfo(ex.getPreviewUp(getFiles(dir, search, sort))) {
            val errors = ex.update()
            if (errors.isNotEmpty()) {
                Info.showErrorSave(errors)
            } else {
                Info.showSuccessSave()
            }
        }
    }

    fun extensionLower(search: String, sort: String) {
        showInfo(ex.getPreviewLower(getFiles(dir, search, sort))) {
            val errors = ex.update()
            if (errors.isNotEmpty()) {
                Info.showErrorSave(errors)
            } else {
                Info.showSuccessSave()
            }
        }
    }

    fun rename(template: String, search: String, zeros: String, sort: String) {
        showInfo(re.getPreview(getFiles(dir, search, sort), template, zeros)) {
            val errors = re.update()
            if (errors.isNotEmpty()) {
                Info.showErrorSave(errors)
            } else {
                Info.showSuccessSave()
            }
        }
    }

    private fun getFiles(dir: String, search: String, sort: String): Array<SortFile> {

        val files = File(dir).walk().filter { it.isFile }.filter { it.name.matches(search.toRegex()) }
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