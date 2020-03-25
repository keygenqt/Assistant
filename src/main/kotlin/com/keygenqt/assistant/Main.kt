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

fun main(args: Array<String>) {
    if (args.isNotEmpty()) {

        var dir = ""
        var template = "{text}-{index}"
        var search = ".*"
        var zeros = "auto"
        var sort = SORT_DEFAULT

        for (value in args) {
            when (val key = value.substring(0, if (value.contains("=")) value.indexOf("=") else value.length)) {
                ARGS_FOLDER_PATH -> {
                    dir = value.replace("$key=", "")
                }
                ARGS_TEMPLATE -> {
                    template = value.replace("$key=", "")
                }
                ARGS_SEARCH -> {
                    search = value.replace("$key=", "")
                }
                ARGS_ZEROS -> {
                    zeros = value.replace("$key=", "")
                }
                ARGS_SORT -> {
                    sort = value.replace("$key=", "")
                }
                ARGS_VERSION -> {
                    Info.showVersion()
                }
                ARGS_HELP -> {
                    Info.showHelp()
                }
            }
        }

        Work(dir, template, search, zeros, sort).run()

    } else {
        Info.showHelp()
    }
}