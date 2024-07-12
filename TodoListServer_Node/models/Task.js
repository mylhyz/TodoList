class Task {
  taskId;
  title;
  description;
  completed;

  constructor(taskId, title, description, completed) {
    this.taskId = taskId;
    this.title = title;
    this.description = description;
    this.completed = completed;
  }
}

module.exports = Task;
