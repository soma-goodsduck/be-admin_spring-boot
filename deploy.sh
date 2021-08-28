#!/usr/bin/env bash

REPOSITORY=/opt/be-admin_spring-boot
cd $REPOSITORY

APP_NAME=be-admin_spring-boot
JAR_PATH=$REPOSITORY/build/libs/

CURRENT_PID=$(pgrep -f $APP_NAME)

echo "현재 구동 중인 애플리케이션 pid: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]
then
  echo "> 종료할것 없음."
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> JAR PATH: $JAR_PATH"
echo "> $JAR_PATH 배포"

JAR_NAME=$(ls $JAR_PATH | grep '.jar' | tail -n 1)

nohup java -jar $JAR_NAME > $JAR_PATH/nohup.out 2>&1 &
