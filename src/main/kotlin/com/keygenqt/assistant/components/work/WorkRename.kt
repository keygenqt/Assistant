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

class WorkRename {

    private val result = linkedMapOf<String, SortFile>()

    fun getPreview(files: Array<SortFile>, template: String, zeros: String): LinkedHashMap<String, SortFile> {

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