#!/usr/bin/python3
import sys
ignoreFile = open("ignore.txt")
ignoreText = ignoreFile.read()
ignoreLines = ignoreText.split("\n")

command = 'find ' + sys.argv[1] + ' -path "*/\.*" -prune'

for line in ignoreLines:
    command += ' -o -regex "' + line + '" -prune'

command += ' -o -regex ".*" -print'
with open("findCom.sh", 'w') as out:
    out.write("#!/bin/bash\n");
    out.write(command)

