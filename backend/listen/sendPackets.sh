#!/bin/bash

declare -i count=0

for j in {1..100}
do
	echo -n "hello" >/dev/udp/localhost/$1
	count=$((count+1))
done
echo "Cycle complete"
echo $count
	
