# TodoList 接口文档

RESTful API 接口分为以下几种，主要也对应数据库中的集中操作---
CRUD（增/查/改/删），也就是 Create/Read/Update/Delete 几种操作，对应的 API 操作分别是 POST/GET/PUT/DELETE 对应，如下表格

| 操作   | 相应 HTTP 方法 |
| ------ | -------------- |
| Create | POST /add      |
| Read   | GET /:id       |
| Update | PUT /:id       |
| Delete | DELETE /:id    |

# 设计

规定接口规范，为了方便，会忽略很多错误控制状态和其他复杂状态

### 1.错误处理

- 错误标志位 `error` 键值为`true`或是`false`
- 错误信息`errMsg`，键值为出错字符串，`error`为`false`时值为`null`

### 2.结果

- 使用`result`作为结果的键，值为如下列表对应的值

| 操作             | 结果值                            |
| ---------------- | --------------------------------- |
| GET /tasks       | 结果列表 List ([{...},{...},...]) |
| GET /task/:id    | 单个结果 ({...})                  |
| POST /task       | 完整的结果对象({...})             |
| PUT /task/:id    | 修改完成的结果对象 ({...})        |
| DELETE /task/:id | 空对象 (`null`)                   |

- 出错无法返回结果时，`result`值为`null`

### 3.结果示例

- Demo Success

```json
{
  "error": false,
  "errMsg": null,
  "result": [
    {
      "taskId": "1234-5678-9010",
      "title": "t1",
      "description": "d1",
      "completed": true
    }
  ]
}
```

- Demo Error

```json
{
  "error": true,
  "errMsg": "can't find the taskId",
  "result": null
}
```
