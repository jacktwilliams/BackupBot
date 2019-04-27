#!/usr/bin/python3
ignoreFile = open("ignore.txt")
ignoreText = ignoreFile.read()
ignoreLines = ignoreText.split("\n")

command = 'find . -path "*/\.*" -prune'

for line in ignoreLines:
    command += ' -o -regex "' + line + '" -prune'

command += ' -o -regex ".*" -print'
with open("findCom.sh", 'w') as out:
    out.write(command)

