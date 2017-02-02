#!/bin/bash

export TEST=`find ~/.m2 -name *.jar | python -c "import sys; print ':'.join(sys.stdin.read().splitlines())"`
sudo java -cp $TEST:target/tweetbot-1.0-SNAPSHOT.jar src.main.java.BotClient
