package main

import (
	"context"
	"log"
	"server/todolist/src/handlers"

	"github.com/labstack/echo/v4"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
)

func main() {
	//数据库
	db := initDB()

	e := echo.New()

	e.GET("/api/v1/tasks", handlers.GetTasks(db))
	e.GET("/api/v1/task/:id", handlers.GetTask(db))
	e.POST("/api/v1/task", handlers.CreateTask(db))
	e.PUT("/api/v1/task/:id", handlers.UpdateTask(db))
	e.DELETE("/api/v1/task/:id", handlers.DeleteTask(db))
	e.Logger.Fatal(e.Start(":8080"))

}

func initDB() *mongo.Collection {
	// 设置MongoDB连接字符串
	connectionString := "mongodb://localhost:27017"
	// 创建一个新的客户端并连接到服务器
	client, err := mongo.Connect(context.TODO(), options.Client().ApplyURI(connectionString))
	if err != nil {
		log.Fatal(err)
	}
	// 检查连接
	err = client.Ping(context.TODO(), nil)
	if err != nil {
		log.Fatal(err)
	}
	log.Println("Connected to MongoDB!")

	db := client.Database("todo-list")
	collection := db.Collection("tasks")
	return collection
}
