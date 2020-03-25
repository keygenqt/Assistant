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

package com.keygenqt.assistant

import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.util.*
import java.util.regex.PatternSyntaxException

class Work(
    private val dir: String,
    private val template: String,
    private val search: String,
    private val zeros: String,
    private val sort: String
) {

    fun run() {
        checkDir(dir)
        checkTemplate(template)
        checkSearch(search)
        checkZeros(zeros)
        checkSort(sort)

        val files = getNames(getFiles(dir, search, sort), template, zeros)
        val errors = saveFiles(files)

        if (errors.isNotEmpty()) {
            Info.showErrorSave(errors)
        } else {
            Info.showSuccessSave()
        }
    }

    private fun getFiles(dir: String, search: String, sort: String): Array<MyFile> {
        val files = File(dir).walk().filter { it.isFile }.filter { it.name.matches(search.toRegex()) }
            .map { MyFile(it.absolutePath) }.toList().toTypedArray()

        when (sort) {
            SORT_MOD -> Arrays.sort(files) { f1, f2 -> f1.lastModified().compareTo(f2.lastModified()) }
            SORT_NATURAL -> Arrays.sort(files)
            else -> {
                Arrays.sort(files) { f1, f2 -> f1.name.compareTo(f2.name, true) }
            }
        }
        return files
    }

    private fun getNames(files: Array<MyFile>, template: String, zeros: String): LinkedHashMap<String, MyFile> {

        val result = linkedMapOf<String, MyFile>()
        val z = if (zeros == "auto") files.size.toString().length else zeros.toInt()

        for ((index, file) in files.withIndex()) {
            var name = template.replace("{text}", file.nameWithoutExtension)
                .replace("{index}", String.format("%0${z}d", index + 1))
            if (file.extension.isNotEmpty()) {
                name = "${name}.${file.extension}"
            }
            result[name] = file
        }

        return result
    }

    private fun saveFiles(files: LinkedHashMap<String, MyFile>): ArrayList<String> {
        val errors = arrayListOf<String>()
        for ((key, value) in files) {
            println(String.format("%-40s -> %s", value.name, key))
        }

        val br = BufferedReader(InputStreamReader(System.`in`))
        print("\nReady? Rename all files (yes/no):")

        when (br.readLine()) {
            "yes" -> {
                for ((key, value) in files) {
                    if (!value.move(key)) {
                        errors.add(value.name)
                    }
                }
            }
            "no" -> {
                Info.showClose()
            }
            else -> {
                Info.showErrorYesNo()
            }
        }
        return errors
    }

    private fun checkDir(dir: String) {
        if (dir.isEmpty()) {
            Info.showErrorDir()
        }
        val f = File(dir)
        if (!f.exists() || f.isFile) {
            Info.showErrorDir()
        }
    }

    private fun checkTemplate(template: String) {
        if (template.isEmpty()) {
            Info.showErrorTemplate()
        }
        if (!(template.contains("{index}"))) {
            Info.showErrorTemplate()
        }
    }

    private fun checkSearch(search: String) {
        if (search.isEmpty()) {
            Info.showErrorSearch()
        }
        try {
            search.toRegex()
        } catch (exception: PatternSyntaxException) {
            println(exception.message)
            Info.showErrorSearch()
        }
    }

    private fun checkZeros(zeros: String) {
        if (zeros.isEmpty()) {
            Info.showErrorZeros()
        }
        if (zeros != "auto" && zeros.toIntOrNull() == null) {
            Info.showErrorZeros()
        }
    }

    private fun checkSort(sort: String) {
        if (sort.isEmpty()) {
            Info.showErrorSort()
        }
        if (sort != SORT_MOD && sort != SORT_NATURAL && sort != SORT_DEFAULT) {
            Info.showErrorSort()
        }
    }
}