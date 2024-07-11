package models

import (
	"context"
	"errors"
	"log"

	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/mongo"
)

type TaskRequestBody struct {
	TaskId      string `json:"taskId"`
	Title       string `json:"title"`
	Description string `json:"description"`
	Completed   bool   `json:"completed"`
}

type Task struct {
	TaskId      string `json:"taskId"`
	Title       string `json:"title"`
	Description string `json:"description"`
	Completed   bool   `json:"completed"`
}

func GetTasks(db *mongo.Collection) ([]bson.M, error) {
	cursor, err := db.Find(context.TODO(), bson.D{})
	if err != nil {
		log.Fatal(err)
	}

	var result []bson.M

	if err := cursor.All(context.TODO(), &result); err != nil {
		log.Fatal(err)
	}
	return result, nil
}

func GetTask(db *mongo.Collection, taskId string) (Task, error) {
	var result Task
	err := db.FindOne(context.TODO(), bson.D{{Key: "taskid", Value: taskId}}).Decode(&result)
	if err != nil {
		return result, err
	}
	return result, nil
}

func CreateTask(db *mongo.Collection, body TaskRequestBody) (interface{}, error) {
	// 这里需要注意的是创建场景下正常是不会有相同的taskId
	count, err := db.CountDocuments(context.TODO(), bson.D{{Key: "taskid", Value: body.TaskId}})
	if err != nil {
		if err != mongo.ErrNoDocuments {
			return nil, err
		}
	}
	if count > 0 {
		return nil, errors.New("duplicated taskId")
	}
	result, err := db.InsertOne(context.TODO(), body)
	if err != nil {
		return nil, err
	}
	return result, nil
}

func UpdateTask(db *mongo.Collection, taskId string, body TaskRequestBody) (*mongo.UpdateResult, error) {
	update := bson.D{{"$set", bson.D{{"title", body.Title}, {"description", body.Description}, {"completed", body.Completed}}}}
	result, err := db.UpdateOne(context.TODO(), bson.D{{Key: "taskid", Value: taskId}}, update)
	if err != nil {
		return nil, err
	}
	return result, nil
}

func DeleteTask(db *mongo.Collection, taskId string) (*mongo.DeleteResult, error) {
	result, err := db.DeleteOne(context.TODO(), bson.D{{Key: "taskid", Value: taskId}})
	if err != nil {
		return nil, err
	}
	return result, nil
}
