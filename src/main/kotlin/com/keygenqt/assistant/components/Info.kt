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

class Info {
    companion object {

        fun showInfo(text: String) {
            println(text)
        }

        fun showInfoExit(text: String) {
            println(text)
            exit()
        }

        fun showHelp() {
            println(STRING_FULL_HELP)
            exit()
        }

        fun showErrorYesNo() {
            println(STRING_GOODBYE)
            exit()
        }

        fun showClose() {
            println(STRING_GOODBYE_NO)
            exit()
        }

        fun showErrorDir() {
            println(STRING_ERROR_DIR)
            exit()
        }

        fun showErrorTemplate() {
            println(STRING_ERROR_TEMPLATE)
            exit()
        }

        fun showErrorSearch() {
            println(STRING_ERROR_SEARCH)
            exit()
        }

        fun showErrorZeros() {
            println(STRING_ERROR_ZEROS)
            exit()
        }

        fun showErrorSort() {
            println(STRING_ERROR_SORT)
            exit()
        }

        fun showSuccessSave() {
            println(STRING_DONE)
            exit()
        }

        fun showErrorSave(errors: ArrayList<String>) {
            println(
                """
                    |
                    |Done. Not found filesyes: ${errors.toString().replace("[\\[\\]]".toRegex(), "")}
                      """.trimMargin()
            )
            exit()
        }

        fun showVersion() {
            println("Assistant Ver $VERSION")
            exit()
        }
    }
}



