#!/bin/bash
echo $@
rsync -aqzR $1 $2
