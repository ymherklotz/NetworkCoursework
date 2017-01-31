#!/bin/bash

export SECPOLICY="file:./policy"

#!/bin/bash
for i in `seq 0 50 2000`
do
    echo "Running client, sending messages: " $i
    java -cp . -Djava.security.policy=$SECPOLICY rmi.RMIClient $1 $i
    sleep 1s
done
