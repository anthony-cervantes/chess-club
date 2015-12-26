#!/bin/bash

set -e -x

UI_REVISION=0.2.0

UI_URL=https://github.com/ChessCorp/chess-club-ui/releases/download/${UI_REVISION}/chess-club-ui-${UI_REVISION}.zip

echo "
  ___           _        _ _ _               _   _ ___
 |_ _|_ __  ___| |_ __ _| | (_)_ __   __ _  | | | |_ _|
  | || '_ \/ __| __/ _' | | | | '_ \ / _' | | | | || |
  | || | | \__ \ || (_| | | | | | | | (_| | | |_| || |
 |___|_| |_|___/\__\__,_|_|_|_|_| |_|\__, |  \___/|___|
                                     |___/

From URL: ${UI_URL}
"

SCRIPT_HOME=$(dirname $0)
WEBAPP_HOME=${SCRIPT_HOME}/../webapp/

rm -rf ${WEBAPP_HOME}
mkdir -p ${WEBAPP_HOME}
cd ${WEBAPP_HOME}

TMPFILE=$(mktemp)
wget ${UI_URL} -O ${TMPFILE}
unzip ${TMPFILE}
rm -f ${TMPFILE}
