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

import com.keygenqt.assistant.components.*
import com.keygenqt.assistant.components.work.*
import com.keygenqt.assistant.utils.*
import java.io.*

val PARAMS = hashMapOf(
    ARGS_FOLDER_PATH to File("").absolutePath,
    ARGS_SEARCH to ".*",
    ARGS_SORT to TYPE_SORT_DEFAULT,
    ARGS_EXCLUDE to "",
    ARGS_EXCLUDE_LINES to "1",

    ARGS_TEMPLATE to "{text}-{index}",
    ARGS_ZEROS to "auto",

    ARGS_EXTENSION_UP to "false",
    ARGS_EXTENSION_LOWER to "false",

    ARGS_STATISTIC to "false",

    ARGS_LINES to "false",
    ARGS_LINES_SEARCH to "false",

    ARGS_SIZE to "false"
)

fun main(args: Array<String>) {

    if (args.isEmpty()) {
        Info.showHelp()
    }

    for (item in args) {
        when (item) {
            ARGS_SIZE -> PARAMS[ARGS_SIZE] = "true"
            ARGS_LINES -> PARAMS[ARGS_LINES] = "true"
            ARGS_STATISTIC -> PARAMS[ARGS_STATISTIC] = "true"
            ARGS_EXTENSION_UP -> PARAMS[ARGS_EXTENSION_UP] = "true"
            ARGS_EXTENSION_LOWER -> PARAMS[ARGS_EXTENSION_LOWER] = "true"
            ARGS_VERSION -> Info.showVersion()
            ARGS_HELP -> Info.showHelp()
            else -> {
                when {
                    item.contains("^$ARGS_LINES_SEARCH\\=.+".toRegex()) -> {
                        PARAMS[ARGS_LINES_SEARCH] = item.replace("$ARGS_LINES_SEARCH=", "")
                        Checker.regex(PARAMS[ARGS_LINES_SEARCH] ?: "")
                    }
                    item.contains("^$ARGS_FOLDER_PATH\\=.+".toRegex()) -> {
                        PARAMS[ARGS_FOLDER_PATH] = item.replace("$ARGS_FOLDER_PATH=", "")
                        Checker.folder(PARAMS[ARGS_FOLDER_PATH] ?: "")
                    }
                    item.contains("^$ARGS_TEMPLATE\\=.+".toRegex()) -> {
                        PARAMS[ARGS_TEMPLATE] = item.replace("$ARGS_TEMPLATE=", "")
                        Checker.template(PARAMS[ARGS_TEMPLATE] ?: "")
                    }
                    item.contains("^$ARGS_SEARCH\\=.+".toRegex()) -> {
                        PARAMS[ARGS_SEARCH] = item.replace("$ARGS_SEARCH=", "")
                        Checker.regex(PARAMS[ARGS_SEARCH] ?: "")
                    }
                    item.contains("^$ARGS_EXCLUDE\\=.+".toRegex()) -> {
                        PARAMS[ARGS_EXCLUDE] = item.replace("$ARGS_EXCLUDE=", "")
                        Checker.regex(PARAMS[ARGS_EXCLUDE] ?: "")
                    }
                    item.contains("^$ARGS_EXCLUDE_LINES\\=.+".toRegex()) -> {
                        PARAMS[ARGS_EXCLUDE_LINES] = item.replace("$ARGS_EXCLUDE_LINES=", "")
                        Checker.int(PARAMS[ARGS_EXCLUDE_LINES] ?: "")
                    }
                    item.contains("^$ARGS_ZEROS\\=.+".toRegex()) -> {
                        PARAMS[ARGS_ZEROS] = item.replace("$ARGS_ZEROS=", "")
                        Checker.zeros(PARAMS[ARGS_ZEROS] ?: "")
                    }
                    item.contains("^$ARGS_SORT\\=.+".toRegex()) -> {
                        PARAMS[ARGS_SORT] = item.replace("$ARGS_SORT=", "")
                        Checker.sort(PARAMS[ARGS_SORT] ?: "")
                    }
                }
            }
        }
    }

    when {
        "${PARAMS[ARGS_LINES]}" != "false" -> {
            if ("${PARAMS[ARGS_LINES_SEARCH]}" == "false") {
                Info.showInfoExit("$ARGS_LINES_SEARCH required!")
            }
            Work(
                "${PARAMS[ARGS_FOLDER_PATH]}",
                "${PARAMS[ARGS_SEARCH]}",
                "${PARAMS[ARGS_SORT]}",
                "${PARAMS[ARGS_EXCLUDE]}",
                "${PARAMS[ARGS_EXCLUDE_LINES]}".toInt()
            ).countLines("${PARAMS[ARGS_LINES_SEARCH]}")
        }
        "${PARAMS[ARGS_SIZE]}" != "false" -> {
            Work(
                "${PARAMS[ARGS_FOLDER_PATH]}",
                "${PARAMS[ARGS_SEARCH]}",
                "${PARAMS[ARGS_SORT]}",
                "${PARAMS[ARGS_EXCLUDE]}",
                "${PARAMS[ARGS_EXCLUDE_LINES]}".toInt()
            ).size()
        }
        "${PARAMS[ARGS_STATISTIC]}" != "false" -> {
            Work(
                "${PARAMS[ARGS_FOLDER_PATH]}",
                "${PARAMS[ARGS_SEARCH]}",
                "${PARAMS[ARGS_SORT]}",
                "${PARAMS[ARGS_EXCLUDE]}",
                "${PARAMS[ARGS_EXCLUDE_LINES]}".toInt()
            ).statistic()
        }
        "${PARAMS[ARGS_EXTENSION_UP]}" == "true" -> {
            Work(
                "${PARAMS[ARGS_FOLDER_PATH]}",
                "${PARAMS[ARGS_SEARCH]}",
                "${PARAMS[ARGS_SORT]}",
                "${PARAMS[ARGS_EXCLUDE]}",
                "${PARAMS[ARGS_EXCLUDE_LINES]}".toInt()
            ).extensionUp()
        }
        "${PARAMS[ARGS_EXTENSION_LOWER]}" == "true" -> {
            Work(
                "${PARAMS[ARGS_FOLDER_PATH]}",
                "${PARAMS[ARGS_SEARCH]}",
                "${PARAMS[ARGS_SORT]}",
                "${PARAMS[ARGS_EXCLUDE]}",
                "${PARAMS[ARGS_EXCLUDE_LINES]}".toInt()
            ).extensionLower()
        }
        "${PARAMS[ARGS_FOLDER_PATH]}".isNotEmpty() -> {
            Work(
                "${PARAMS[ARGS_FOLDER_PATH]}",
                "${PARAMS[ARGS_SEARCH]}",
                "${PARAMS[ARGS_SORT]}",
                "${PARAMS[ARGS_EXCLUDE]}",
                "${PARAMS[ARGS_EXCLUDE_LINES]}".toInt()
            ).rename(
                "${PARAMS[ARGS_TEMPLATE]}",
                "${PARAMS[ARGS_ZEROS]}"
            )
        }
    }

    exit()
}