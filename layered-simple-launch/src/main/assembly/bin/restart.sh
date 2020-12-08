#!/bin/bash
cd `dirname $0`
echo "Stop server!"
source ./shutdown.sh $1

echo ""
echo "Start new server!"
source ./startup.sh