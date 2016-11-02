#!/bin/bash
for i in {1..1000}
do
	echo -n "hello" >/dev/udp/localhost/$1
done
