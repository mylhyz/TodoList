#!/bin/bash

# POST http://localhost:8080/api/v1/task 
# {
#    "taskId":"1234-5678-9010",
#    "title":"t",
#    "description":"d",
#    "completed":false
# }


URL="http://localhost:8080/api/v1/task"

REQUEST_COUNT=5

for ((i=1;i<=REQUEST_COUNT;i++))
do
    # 生成随机数，范围可以根据需要调整
    RANDOM_NUM=$(( (RANDOM % 10000) + 1 ))
    # 生成UUID
    UUID=$(uuidgen)
    # 构建payload
    PAYLOAD="{\"title\": \"task-$i\",\"completed\": false,\"description\": \"desc-$RANDOM_NUM\", \"taskId\": \"$UUID\"}"
    echo "发起第 $i 次请求" $PAYLOAD
    curl -X POST "$URL" -H "Content-Type: application/json" -d "$PAYLOAD"
done

echo "所有请求发送完毕"