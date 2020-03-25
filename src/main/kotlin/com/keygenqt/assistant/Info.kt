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

import kotlin.system.exitProcess

class Info {
    companion object {
        fun showHelp() {
            println(
                """
                    |Usage: assistant COMMAND=ARG...
                    |
                    |Assistant is a delightful multi file rename tool.
                    |
                    |Options:
                    |  --dir         Path to folder with files.
                    |  --template    Template for name {body} - name file, {index} - set up index
                    |  --search      Pattern regex for search file in folder. If empty - all.
                    |  --zeros       Size count zeros in index. If empty - disable. If 0 - auto.
                    |  --sort        Sort files: {default} - by names standard, {natural} - by names natural, {mod} - last modification
                    |  --version     Show the version and exit.
                    """.trimMargin()
            )
            exitProcess(0)
        }

        fun showErrorDir() {
            println(
                """
                    |Usage: assistant COMMAND=ARG...
                    |            
                    |Error option:
                    |  --dir         Path to folder with files.
                    |  
                    |Use for get full help:
                    |  --help
                      """.trimMargin()
            )
            exitProcess(0)
        }

        fun showErrorTemplate() {
            println(
                """
                    |Usage: assistant COMMAND=ARG...
                    |            
                    |Error option:
                    |  --template    Template for name {body} - name file, {index} - set up index
                    |  
                    |Use for get full help:
                    |  --help
                      """.trimMargin()
            )
            exitProcess(0)
        }

        fun showErrorSearch() {
            println(
                """
                    |Usage: assistant COMMAND=ARG...
                    |            
                    |Error option:
                    |  --search      Pattern regex for search file in folder. If empty - all.
                    |  
                    |Use for get full help:
                    |  --help
                      """.trimMargin()
            )
            exitProcess(0)
        }

        fun showErrorZeros() {
            println(
                """
                    |Usage: assistant COMMAND=ARG...
                    |            
                    |Error option:
                    |  --zeros       Size count zeros in index. If empty - disable. If 0 - auto.
                    |  
                    |Use for get full help:
                    |  --help
                      """.trimMargin()
            )
            exitProcess(0)
        }

        fun showErrorSort() {
            println(
                """
                    |Usage: assistant COMMAND=ARG...
                    |            
                    |Error option:
                    |  --sort        Sort files: {default} - by names standard, {natural} - by names natural, {mod} - last modification
                    |  
                    |Use for get full help:
                    |  --help
                      """.trimMargin()
            )
            exitProcess(0)
        }

        fun showVersion() {
            println("Assistant Ver $VERSION")
            exitProcess(0)
        }

        fun showSuccessSave() {
            println(
                """
                    |
                    |Updated files success
                      """.trimMargin()
            )
            exitProcess(0)
        }

        fun showErrorSave(errors: ArrayList<String>) {
            println(
                """
                    |
                    |Done. Not found filesyes: ${errors.toString().replace("[\\[\\]]".toRegex(), "")}
                      """.trimMargin()
            )
            exitProcess(0)
        }

        fun showClose() {
            println(
                """
                    |
                    |Ok, goodbye. Come back again!
                      """.trimMargin()
            )
            exitProcess(0)
        }

        fun showErrorYesNo() {
            println(
                """
                    |
                    |You not said "no", but goodbye anyway :)
                      """.trimMargin()
            )
            exitProcess(0)
        }
    }
}



