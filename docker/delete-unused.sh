#!/bin/bash

root=/usr/share/java
unused=(
        kafka-connect-activemq
        kafka-connect-s3
        kafka-connect-ibmmq
        kafka-connect-jms
        confluent-control-center
        kafka-connect-jdbc
)

for file in ${unused[@]}
do
        rm -rf $root/$file
done
