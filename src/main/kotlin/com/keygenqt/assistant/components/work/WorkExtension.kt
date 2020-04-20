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
import java.util.*

class WorkExtension() {

    private val result = linkedMapOf<String, SortFile>()

    fun getPreviewUp(files: Array<SortFile>): LinkedHashMap<String, SortFile> {
        return getPreview(files)
    }

    fun getPreviewLower(files: Array<SortFile>): LinkedHashMap<String, SortFile> {
        return getPreview(files, "lower")
    }

    private fun getPreview(files: Array<SortFile>, type: String = "upper"): LinkedHashMap<String, SortFile> {
        for (file in files) {
            if (file.extension.isNotEmpty()) {
                if (type == "upper") {
                    result["${file.nameWithoutExtension}.${file.extension.toUpperCase()}"] = file
                } else if (type == "lower") {
                    result["${file.nameWithoutExtension}.${file.extension.toLowerCase()}"] = file
                }
            }
        }
        return result
    }

    fun update(): ArrayList<String> {
        val errors = arrayListOf<String>()
        for ((key, value) in result) {
            if (!value.move(key)) {
                errors.add(value.name)
            }
        }
        return errors;
    }
}