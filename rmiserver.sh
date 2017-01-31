#!/bin/bash

export SECPOLICY="file:./policy"
mkdir -p test_output
java -cp . -Djava.security.policy=$SECPOLICY rmi.RMIServer | tee test_output/server_out.txt
