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

package com.keygenqt.assistant.components

import com.keygenqt.assistant.utils.*
import java.io.*
import java.util.regex.*

class Checker {
    companion object {
        fun folder(value: String) {
            if (value.isEmpty()) {
                Info.showErrorDir()
            }
            val f = File(value)
            if (!f.exists() || f.isFile) {
                Info.showErrorDir()
            }
        }

        fun template(value: String) {
            if (value.isEmpty()) {
                Info.showErrorTemplate()
            }
            if (!value.contains("{index}")) {
                Info.showErrorTemplate()
            }
        }

        fun regex(value: String) {
            if (value.isEmpty()) {
                Info.showErrorSearch()
            }
            try {
                value.toRegex()
            } catch (exception: PatternSyntaxException) {
                exception.message?.let { Info.showInfo(it) }
                Info.showErrorSearch()
            }
        }

        fun zeros(value: String) {
            if (value.isEmpty()) {
                Info.showErrorZeros()
            }
            value.toIntOrNull()?.let {
                if (it > 20) {
                    Info.showErrorZeros()
                }
            } ?: run {
                Info.showErrorZeros()
            }
        }

        fun sort(value: String) {
            if (value.isEmpty()) {
                Info.showErrorSort()
            }
            when (value) {
                TYPE_SORT_DEFAULT -> {
                }
                TYPE_SORT_NATURAL -> {
                }
                TYPE_SORT_LAST_MOD -> {
                }
                else -> Info.showErrorSort()
            }
        }
    }
}