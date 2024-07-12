const express = require("express");
const Task = require("../../models/Task");
const Result = require("../../models/Result");
const MongoDB = require("../../models/Database");
const router = express.Router(); //router导出

// 初始化MongoDB

//API设计第一版

/**
 * 查询所有数据
 */
router.get("/tasks", async function (req, res) {
  try {
    let tasks = await MongoDB.getTasks();
    const result = new Result(false, null, tasks.reverse());
    res.send(result);
  } catch (err) {
    const result = new Result(true, err.message, null);
    res.send(result);
  }
});

/**
 * 查询单条数据
 */
router.get("/task/:id", async function (req, res) {
  const taskId = req.params.id;
  try {
    let tasks = [];
    let task = await MongoDB.get(taskId);
    if (task) {
      tasks.push(task);
    }
    const result = new Result(false, null, tasks);
    res.send(result);
  } catch (err) {
    const result = new Result(true, err.message, null);
    res.send(result);
  }
});

/**
 * 添加一条数据
 *
 * request body Sample title="hello",description="world"
 */
router.post("/task", async function (req, res) {
  const taskId = req.body.taskId;
  const title = req.body.title;
  const description = req.body.description;
  const newTask = new Task(taskId, title, description, false);
  try {
    const ret = await MongoDB.insert(newTask);
    console.log(`INSERT ${ret.insertedId}`);
    const result = new Result(false, null, [newTask]);
    res.send(result);
  } catch (err) {
    const result = new Result(true, err.message, null);
    res.send(result);
  }
});

/**
 * 删除一条数据
 *
 * 数据由id标识
 */
router.delete("/task/:id", async function (req, res) {
  const taskId = req.params.id;
  try {
    const ret = await MongoDB.delete(taskId);
    console.log(`DELETE ${ret.deletedCount}`);
    const result = new Result(false, null, null);
    res.send(result);
  } catch (err) {
    const result = new Result(true, err.message, null);
    res.send(result);
  }
});

/**
 * 更新一条数据
 *
 * 数据通过request body传输，格式为json（不是json数组）
 *
 * Sample : params={"completed":"true"}
 *          params={"completed":"true","description":"测试数据"}
 */
router.put("/task/:id", async function (req, res) {
  const taskId = req.params.id;
  const title = req.body.title;
  const description = req.body.description;
  const completed = req.body.completed;
  try {
    const ret = await MongoDB.update(taskId, {
      title: title,
      description: description,
      completed: completed,
    });
    console.log(
      `UPDATE ${ret.matchedCount}/${ret.modifiedCount} : ${ret.upsertedCount}/${ret.upsertedId}`
    );
    const result = new Result(false, null, [
      { taskId, title, description, completed },
    ]);
    res.send(result);
  } catch (err) {
    const result = new Result(true, err.message, null);
    res.send(result);
  }
});

module.exports = router;
