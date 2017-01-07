#!/usr/bin/env bash

#{
#   "account":{
#      "name":"testaccount"
#   },
#   "device":{
#      "imei":"xyz"
#   }
#}

#'{"account":{"name":"testaccount"},"device":{"imei":"xyz"}}'


curl -H "Content-Type: application/json" -X POST -d '{"account":{"name":"testaccount"},"device":{"imei":"xyz"}}' http://localhost:7001/device




