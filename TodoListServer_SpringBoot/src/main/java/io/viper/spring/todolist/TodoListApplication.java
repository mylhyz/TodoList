package io.viper.spring.todolist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@SpringBootApplication
public class TodoListApplication {

    @Autowired
    private TaskRepository taskRepository;

    public static void main(String[] args) {
        SpringApplication.run(TodoListApplication.class, args);
    }

    @GetMapping("/task")
    List<Task> getAllTasks() {
        // 从数据库中查询所有的数据并返回
        return taskRepository.findAll();
    }

    @GetMapping("/task/{id}")
    Task findTask(@PathVariable() String id) {
        return taskRepository.findById(id);
    }

    @PostMapping("/task")
    Task addTask(@RequestBody Task task) {
        Task old = taskRepository.findById(task.getId());
        if (old == null) {
            return taskRepository.save(task);
        }
        // 报错
        return null;
    }

    @DeleteMapping("/task/{id}")
    Task deleteTask(@PathVariable() String id) {
        Task task = taskRepository.findById(id);
        if (task != null) {
            taskRepository.delete(task);
        }
        return task;
    }

    @PutMapping("/task/{id}")
    Task updateTask(@PathVariable() String id, @RequestBody Task task) {
        Task old = taskRepository.findById(id);
        if (old != null) {
            taskRepository.saveAndFlush(task);
        } else {
            // 报错
            return null;
        }
        return task;
    }
}
