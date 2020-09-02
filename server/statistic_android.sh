#!/bin/bash

# ./statistic_android.sh -d=/home/keygenqt/project -t=1.0.0

#NOW (HEAD)
#kotlin files: 222, code lines: 00000
#java   files: 222, code lines: 00000
#xml    files: 222, code lines: 00000
#-------
#ALL    files: 666, code lines: 00000
#
#OLD (was before 1.0.0)
#kotlin files: 333, code lines: 00000
#java   files: 333, code lines: 00000
#xml    files: 333, code lines: 00000
#-------
#ALL    files: 999, code lines: 00000
#
#2.8.0 -> HEAD (last tag: 1.0.0)
#kotlin files: +33, code lines: +00000
#java   files: -33, code lines: -00000
#xml    files: -33, code lines: -00000
#-------
#ALL    files: -66, code lines: -00000

# add color red
CLEAR='\033[0m'
RED='\033[0;31m'
GREEN='\033[0;32m'
BLUE='\033[34m'

# load arguments
for i in "$@"; do
  case $i in
  -t=* | --tag=*)
    TAG="${i#*=}"
    shift
    ;;
  -d=* | --dir=*)
    DIR="${i#*=}"
    shift
    ;;
  *)
    echo -e "${RED}"Unknown parameter passed: $i"${CLEAR}"
    exit 1
    ;;
  esac
done

# check required argument
if [ -z "$DIR" ]; then
  echo -e "${RED}Dir (-d=*|--dir=*) required argument${CLEAR}"
  exit 1
fi

if [ ! -d "$DIR" ]; then
  echo -e "${RED}Dir not exist${CLEAR}"
  exit 1
fi

# get last tag if empty
LAST_TAG=$(cd "${DIR}" && git tag --sort=committerdate | tail -1)

if [ "$TAG" == "" ]; then
  TAG="$LAST_TAG"
fi

# example rm temp dir
TEMP_DIR="$DIR/app/src/main/java/_generate"

if [ -d "$TEMP_DIR" ]; then
  rm -rf "$TEMP_DIR"
fi

# check res/layout xml
RES_DIR="$DIR/app/src/main/res/layout"

# просто костыль
if [ ! -d "$RES_DIR" ]; then
  RES_DIR="$DIR/app/src/main/res-screen"
fi

#parse info xml
xml=$(./statistic.sh -dg="$DIR" -d="$RES_DIR" -s=".*\.xml" -t="$TAG" | sed 's/\x1B\[[0-9;]\{1,\}[A-Za-z]//g' | xargs)
countingLinesXml=$(echo "$xml" | sed -E "s/.+Counting//" | sed -E "s/.+lines\://" | xargs)
countingFilesXml=$(echo "$xml" | sed -E "s/.+Counting//" | sed -E "s/.+files\:+//" | sed "s/code lines: $countingLinesXml//" | xargs)
newStatXml=$(echo "$xml" | sed -E "s/TAG.+//" | sed -E "s/.+files\://")
oldStatXml=$(echo "$xml" | sed -E "s/Counting.+//" | sed -E "s/.+TAG//" | sed -E "s/.+files\://")
newFilesXml=$(echo "$newStatXml" | sed -E "s/code.+//" | xargs)
newLinesXml=$(echo "$newStatXml" | sed -E "s/.+code//" | sed -E "s/lines\://" | xargs)
oldFilesXml=$(echo "$oldStatXml" | sed -E "s/code.+//" | xargs)
oldLinesXml=$(echo "$oldStatXml" | sed -E "s/.+code//" | sed -E "s/lines\://" | xargs)

#parse info java
java=$(./statistic.sh -dg="$DIR" -d="$DIR/app/src/main" -s=".*\.java" -t="$TAG" | sed 's/\x1B\[[0-9;]\{1,\}[A-Za-z]//g' | xargs)
countingLinesJava=$(echo "$java" | sed -E "s/.+Counting//" | sed -E "s/.+lines\://" | xargs)
countingFilesJava=$(echo "$java" | sed -E "s/.+Counting//" | sed -E "s/.+files\:+//" | sed "s/code lines: $countingLinesJava//" | xargs)
newStatJava=$(echo "$java" | sed -E "s/TAG.+//" | sed -E "s/.+files\://")
oldStatJava=$(echo "$java" | sed -E "s/Counting.+//" | sed -E "s/.+TAG//" | sed -E "s/.+files\://")
newFilesJava=$(echo "$newStatJava" | sed -E "s/code.+//" | xargs)
newLinesJava=$(echo "$newStatJava" | sed -E "s/.+code//" | sed -E "s/lines\://" | xargs)
oldFilesJava=$(echo "$oldStatJava" | sed -E "s/code.+//" | xargs)
oldLinesJava=$(echo "$oldStatJava" | sed -E "s/.+code//" | sed -E "s/lines\://" | xargs)

#parse info kotlin
kt=$(./statistic.sh -dg="$DIR" -d="$DIR/app/src/main" -s=".*\.kt" -t="$TAG" | sed 's/\x1B\[[0-9;]\{1,\}[A-Za-z]//g' | xargs)
countingLinesKt=$(echo "$kt" | sed -E "s/.+Counting//" | sed -E "s/.+lines\://" | xargs)
countingFilesKt=$(echo "$kt" | sed -E "s/.+Counting//" | sed -E "s/.+files\:+//" | sed "s/code lines: $countingLinesKt//" | xargs)
newStatKt=$(echo "$kt" | sed -E "s/TAG.+//" | sed -E "s/.+files\://")
oldStatKt=$(echo "$kt" | sed -E "s/Counting.+//" | sed -E "s/.+TAG//" | sed -E "s/.+files\://")
newFilesKt=$(echo "$newStatKt" | sed -E "s/code.+//" | xargs)
newLinesKt=$(echo "$newStatKt" | sed -E "s/.+code//" | sed -E "s/lines\://" | xargs)
oldFilesKt=$(echo "$oldStatKt" | sed -E "s/code.+//" | xargs)
oldLinesKt=$(echo "$oldStatKt" | sed -E "s/.+code//" | sed -E "s/lines\://" | xargs)

# get sum
newFilesAll=$(("$newFilesKt" + "$newFilesJava" + "$newFilesXml"))
oldFilesAll=$(("$oldFilesKt" + "$oldFilesJava" + "$oldFilesXml"))

newLinesAll=$(("$newLinesKt" + "$newLinesJava" + "$newLinesXml"))
oldLinesAll=$(("$oldLinesKt" + "$oldLinesJava" + "$oldLinesXml"))

countFilesAll=$(("$countingFilesXml" + "$countingFilesJava" + "$countingFilesKt"))
countLinesAll=$(("$countingLinesXml" + "$countingLinesJava" + "$countingLinesKt"))

echo -e "${RED}NOW (HEAD)${CLEAR}"
echo -e "${GREEN}kotlin${CLEAR} files:$newStatKt" | sed -E "s/ code/, code/"
echo -e "${GREEN}java  ${CLEAR} files:$newStatJava" | sed -E "s/ code/, code/"
echo -e "${GREEN}xml   ${CLEAR} files:$newStatXml" | sed -E "s/ code/, code/"
echo "-------"
echo -e "${BLUE}ALL   ${CLEAR} files: $newFilesAll, code lines: $newLinesAll"

echo ""
echo -e "${RED}OLD (was before $TAG)${CLEAR}"
echo -e "${GREEN}kotlin${CLEAR} files:$oldStatKt" | sed -E "s/ code/, code/"
echo -e "${GREEN}java  ${CLEAR} files:$oldStatJava" | sed -E "s/ code/, code/"
echo -e "${GREEN}xml   ${CLEAR} files:$oldStatXml" | sed -E "s/ code/, code/"
echo "-------"
echo -e "${BLUE}ALL   ${CLEAR} files: $oldFilesAll, code lines: $oldLinesAll"

echo ""
echo -e "${RED}$TAG -> HEAD${CLEAR} (last tag: $LAST_TAG)"
echo -e "${GREEN}kotlin${CLEAR} files: $countingFilesKt, code lines: $countingLinesKt"
echo -e "${GREEN}java  ${CLEAR} files: $countingFilesJava, code lines: $countingLinesJava"
echo -e "${GREEN}xml   ${CLEAR} files: $countingFilesXml, code lines: $countingLinesXml"
echo "-------"
echo -e "${BLUE}ALL   ${CLEAR} files: $countFilesAll, code lines: $countLinesAll"
