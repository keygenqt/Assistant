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

package com.keygenqt.assistant.utils

const val STRING_FULL_HELP = """
Usage: kg-assistant COMMAND=ARG...

Assistant is a delightful files mod tool.

Options:
    --dir               Path to folder with files.
    --search            Pattern regex for search file in folder. If empty - all.
    --sort              Sort files: {natural} - by natural, {mod} - last modification
    --exclude           Pattern regex for exclude file by content
    --exclude-lines     Exclude lines content

  rename    
    --template          Template for name {text} - name file, {index} - set up index
    --zeros             Size count zeros in index. If empty - disable. If 0 - auto.

  extension
    --extension-up      Uppercase extension in folder
    --extension-lower   Lowercase extension in folder

  statistic
    --statistic   Lowercase extension in folder

  lines
    --lines             Find lines in dir and counting
    --lines-search      Pattern regex for search lines
    
  other
    --size              Get size files
    --version           Show the version and exit
    --help              Show help
"""

const val STRING_DONE = """
Updated files success
"""

const val STRING_GOODBYE_NO = """
Ok, goodbye. Come back again!
"""

const val STRING_GOODBYE = """
You not said "no" or "yes", but goodbye anyway
"""

const val STRING_ERROR_DIR = """
Problem with option --dir
"""

const val STRING_ERROR_TEMPLATE = """
Problem with option --template
"""

const val STRING_ERROR_SEARCH = """
Problem with option --search
"""

const val STRING_ERROR_ZEROS = """
Problem with option --zeros (max size = 20)
"""

const val STRING_ERROR_SORT = """
Problem with option --sort (default/natural/mod)
"""

const val STRING_NOT_FOUND_FILES = """
Files not found.
"""

