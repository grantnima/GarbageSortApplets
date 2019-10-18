#!/bin/bash

project='garbage-sort'
version='1.0.0'

echo "=================================="
echo "部署${project}-${version}"
echo "=================================="

PROJECT_HOME=/opt/deploy/$project

mkdir -p $PROJECT_HOME/release
mkdir -p $PROJECT_HOME/shared
mkdir -p $PROJECT_HOME/shared/config
mkdir -p $PROJECT_HOME/shared/pids
mkdir -p $PROJECT_HOME/shared/log

cd ~/

echo '[开始]解压程序包并重启程序'
if [ -f "$project-$version.tar.gz" ]; then

  tar -xzvf $project-$version.tar.gz -C $PROJECT_HOME/release/

  if [ -L "$PROJECT_HOME/current" ]; then
    $PROJECT_HOME/current/bin/bootstrap.sh stop
  fi

  rm -rf $PROJECT_HOME/current/log
  rm -rf $PROJECT_HOME/current

  ln -s $PROJECT_HOME/release/$project-$version/ $PROJECT_HOME/current
  ln -s $PROJECT_HOME/shared/log/ $PROJECT_HOME/current/log
   echo '[开始]重启程序'
   $PROJECT_HOME/current/bin/bootstrap.sh restart
   echo '[完成]重启程序'
else
  echo "不存在$project-$version.tar.gz"
  exit 1
fi
echo '[完成]解压程序包并重启程序'
