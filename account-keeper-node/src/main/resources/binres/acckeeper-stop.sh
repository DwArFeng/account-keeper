#!/bin/sh
# 程序的根目录
basedir="/usr/local/acckeeper"

PID=$(cat "$basedir/acckeeper.pid")
kill "$PID"
