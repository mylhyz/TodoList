const mongo = require("mongodb");
const MongoClient = mongo.MongoClient;
const config = require("../config.json");
const url = "mongodb://" + config.db_host + ":" + config.db_port;

class MongoDB {
  async connect() {
    const client = new MongoClient(url);
    await client.connect();
    const db = client.db(config.db_database);
    const collection = db.collection(config.db_collection);
    return collection;
  }

  /**
   * 获取所有数据
   */
  async getTasks() {
    const db = await this.connect();
    return await db.find({}).toArray();
  }
  /**
   * 获取数据
   * @param taskId UUID
   */
  async get(taskId) {
    const db = await this.connect();
    return db.findOne({ taskId: taskId });
  }
  /**
   * 保存一条数据到库
   *
   * @param task 一条数据
   */
  async insert(task) {
    const _task = {
      taskId: task.taskId,
      title: task.title,
      description: task.description,
      completed: task.completed,
    };
    const db = await this.connect();
    return db.insertOne(_task);
  }
  /**
   * 通过taskId匹配更新一条数据
   * 更新一条数据
   * @param taskId
   * @param params 参数是json类型的
   */
  async update(taskId, params) {
    const db = await this.connect();
    return db.updateOne({ taskId: taskId }, { $set: params });
  }
  /**
   * 删除一条数据
   * @param taskId
   */
  async delete(taskId) {
    const db = await this.connect();
    return db.deleteOne({ taskId: taskId });
  }
}

// DB.prototype.deleteCompleted = function (callback) {
//   MongoClient.connect(url, function (err, db) {
//     if (err) {
//       return callback(err);
//     }
//     db.collection("tasks").deleteMany({ completed: true }, callback);
//     db.close();
//   });
// };

module.exports = new MongoDB();
