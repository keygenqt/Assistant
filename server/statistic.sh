#!/bin/bash

# add color red
CLEAR='\033[0m'
RED='\033[0;31m'

# load arguments
for i in "$@"; do
  case $i in
  -d=* | --dir=*)
    DIR="${i#*=}"
    shift
    ;;
  -s=* | --search=*)
    SEARCH="${i#*=}"
    shift
    ;;
  -ex=* | --exclude=*)
    EXCLUDE="${i#*=}"
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

# check required argument
if [ -z "$SEARCH" ]; then
  echo -e "${RED}Search (-s=*|--search=*) required argument${CLEAR}"
  exit 1
fi

# set default argument
if [ -z "$EXCLUDE" ]; then
  EXCLUDE="^\/\/.*"
fi

# get statistic now
st=$(kg-assistant --dir="$DIR" --statistic --search="$SEARCH" --exclude="$EXCLUDE" --exclude-lines=1)

# get count files
files=$(echo "$st" | sed -n 1p | sed -e "s/\s//g" | sed -e "s/countfiles\://g")

# get count lines
lines=$(echo "$st" | sed -n 2p | sed -e "s/\s//g" | sed -e "s/countlines\://g")

#copy dir for revert
cp -r "$DIR" "${DIR}_"

#open dir
cd "${DIR}_" || exit

#get last tag
tag=$(git tag | sed -n 1p)

if [ -z "$tag" ]; then
  echo -e "${RED}Tags not found${CLEAR}\n"
  echo -e "${RED}Only HEAD statistic${CLEAR}"
  echo "files: $files"
  echo "code lines: $lines"
  exit 0
fi

#git revert to tag
git reset --hard "$tag" --quiet

# get statistic tag
st=$(kg-assistant --dir="${DIR}_" --statistic --search="$SEARCH" --exclude="$EXCLUDE" --exclude-lines=1)

# get count files
filesTag=$(echo "$st" | sed -n 1p | sed -e "s/\s//g" | sed -e "s/countfiles\://g")

# get count lines
linesTag=$(echo "$st" | sed -n 2p | sed -e "s/\s//g" | sed -e "s/countlines\://g")

# remove temp dir
rm -rf "${DIR}_"

# files count
filesCount=$(($files - $filesTag))

# lines count
linesCount=$(($lines - $linesTag))

# show HEAD statistic
echo -e "${RED}HEAD statistic${CLEAR}"
echo "files: $files"
echo "code lines: $lines"

echo ""

# show TAG statistic
echo -e "${RED}TAG statistic ($tag)${CLEAR}"
echo "files: $filesTag"
echo "code lines: $linesTag"

echo ""

# show counting
echo -e "${RED}Counting${CLEAR}"

if [ "$filesCount" -gt "0" ]; then
  echo "files: +$filesCount"
else
  echo "files: $filesCount"
fi

if [ "$linesCount" -gt "0" ]; then
  echo "code lines: +$linesCount"
else
  echo "code lines: $linesCount"
fi

exit 0
