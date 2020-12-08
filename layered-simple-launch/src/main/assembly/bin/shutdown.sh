#!/usr/bin/env bash

#进入当前脚本目录
cd `dirname $0`
PID_R=$1
BIN_DIR=`pwd`

count=`find  $BIN_DIR -name "$PID_R.pid" | wc -l`
if [ 0 -eq $count ];
then
   echo "No running services!"
   exit 1
fi

#查找pid文件
for pid_file in `find  $BIN_DIR -name "$PID_R.pid"`
do
    PID=`cat ${pid_file}`
    SEL_PID=`ps -ef | grep java | grep "${BIN_DIR}" | grep "${PID}" |awk '{print $2}'`
    if [ -n ${SEL_PID} ];
    then
        kill -9 ${PID}
        echo "SUCCESS: the process ${PID} is stopped !"
    else
        echo "ERROR: the process ${PID} has stopped !"
    fi

    rm ${pid_file}

done