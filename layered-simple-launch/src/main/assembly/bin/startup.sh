#!/bin/bash
SERVER_NAME=${project.name}
# jar名称
JAR_NAME=${artifactId}-${version}.jar
cd `dirname $0`
BIN_DIR=`pwd`
#echo $BIN_DIR
cd ./
DEPLOY_DIR=`pwd`
#echo $DEPLOY_DIR
DEPLOY_DIR="$DEPLOY_DIR/.."

#配置文件路径
CONF_DIR=$DEPLOY_DIR/conf

#判断程序是否已在运行(如程序需支持多个同时运行,注释以下代码)
#for pid_file in `find $BIN_DIR -name "*.pid"`
#do
#   PID=`cat ${pid_file}`
#    SEL_PID=`ps -ef | grep java | grep $DEPLOY_DIR | grep ${PID} | awk '{print $2}'`
#    if [ ${SEL_PID} ];
#    then
#        echo "The $SERVER_NAME is running...! "
#        echo "Pid is ${SEL_PID}"
#        exit 1
#    else
#        echo "The $SERVER_NAME is stopped"
#        echo "Remove file ${pid_file}"
#        rm ${pid_file}
##    fi
#done

# 获取应用的端口号(针对需占用端口的应用)
#SERVER_PORT=`sed -nr '/port: [0-9]+/ s/.*port: +([0-9]+).*/\1/p' $CONF_DIR/application.yml`
#if [ -n "$SERVER_PORT" ]; then
#  SERVER_PORT_COUNT=`netstat -tln | grep $SERVER_PORT | wc -l`
#  if [ $SERVER_PORT_COUNT -gt 0 ]; then
#    echo "ERROR: The $SERVER_NAME port $SERVER_PORT already used!"
#    exit 1
#  fi
#fi

#第三方依赖包路径
LIB_DIR=$DEPLOY_DIR/lib/dependencys

#日志文件路径
LOGS_DIR=$DEPLOY_DIR/logs
if [ ! -d $LOGS_DIR ]; then
  mkdir $LOGS_DIR
fi

STDOUT_FILE=$LOGS_DIR/stdout.log
JAVA_OPTS=" -Djava.awt.headless=true -Djava.net.preferIPv4Stack=true "
JAVA_DEBUG_OPTS=""
if [ "$1" = "debug" ]; then
  JAVA_DEBUG_OPTS=" -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=8000,server=y,suspend=n "
  echo "JAVA_DEBUG_OPTS: $JAVA_DEBUG_OPTS"
fi
JAVA_JMX_OPTS=""
if [ "$1" = "jmx" ]; then
  JAVA_JMX_OPTS=" -Dcom.sun.management.jmxremote.port=1099 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false "
  echo "JAVA_JMX_OPTS  : $JAVA_JMX_OPTS"
fi
JAVA_MEM_OPTS=""

for key in "$@"
do
    if [ "$key" = "-server" -o "$JAVA_MEM_OPTS" != "" ]; then
        JAVA_MEM_OPTS="$JAVA_MEM_OPTS $key"
    fi
done
echo $JAVA_MEM_OPTS
if [ "$JAVA_MEM_OPTS" = "" ]; then
    BITS=`java -version 2>&1 | grep -i 64-bit`
    if [ -n "$BITS" ]; then
      JAVA_MEM_OPTS=" -server -Xmx512m -Xms512m -Xmn256m -XX:PermSize=128m -Xss256k -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:LargePageSizeInBytes=128m -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 "
    else
      JAVA_MEM_OPTS=" -server -Xms512m -Xmx512m -XX:PermSize=128m -XX:SurvivorRatio=2 -XX:+UseParallelGC "
    fi
fi
CONFIG_FILES=" -Dloader.path=$LIB_DIR -Dlogging.path=$LOGS_DIR -Dlogging.config=$CONF_DIR/log4j2.xml -Dspring.config.location=$CONF_DIR/application.yml "

echo `java -version`
echo "JAVA_OPTS      : $JAVA_OPTS"
echo "JAVA_MEM_OPTS  : $JAVA_MEM_OPTS"
echo "CONFIG_FILES   : $CONFIG_FILES"
echo ""

echo -e "Starting the $SERVER_NAME ..."
nohup java $JAVA_OPTS $JAVA_MEM_OPTS $JAVA_DEBUG_OPTS $JAVA_JMX_OPTS $CONFIG_FILES -jar $DEPLOY_DIR/lib/$JAR_NAME $param > /dev/null 2>&1 &

#PIDS=`ps -f | grep java | grep $DEPLOY_DIR | awk '{print $2}'`
PIDS=$!
echo "STDOUT: $STDOUT_FILE"
echo $PIDS > $DEPLOY_DIR/bin/${PIDS}.pid
echo -e "The $SERVER_NAME startup! PID: $PIDS"
echo ""