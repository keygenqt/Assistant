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

import java.text.*
import kotlin.math.*

class Helper {
    companion object {
        fun getNaturalSize(size: Long): String {
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
            return String.format("%.3f %ciB", value / 1024.0, ci.current())
        }

        fun getNaturalSizeSI(size: Long): String {
            var bytes = size
            if (-1000 < bytes && bytes < 1000) {
                return "$bytes B"
            }
            val ci: CharacterIterator = StringCharacterIterator("kMGTPE")
            while (bytes <= -999950 || bytes >= 999950) {
                bytes /= 1000
                ci.next()
            }
            return String.format("%.3f %cB", bytes / 1000.0, ci.current())
        }
    }
}