package handlers

import (
	"net/http"
	"server/todolist/src/models"

	"github.com/labstack/echo/v4"
	"go.mongodb.org/mongo-driver/mongo"
)

func GetTasks(db *mongo.Collection) echo.HandlerFunc {
	return func(c echo.Context) error {
		result, err := models.GetTasks(db)
		if err == nil {
			return c.JSON(http.StatusOK, result)
		} else {
			return c.JSON(http.StatusInternalServerError, err.Error())
		}
	}
}

func GetTask(db *mongo.Collection) echo.HandlerFunc {
	return func(c echo.Context) error {
		id := c.Param("id")
		result, err := models.GetTask(db, id)
		if err == nil {
			return c.JSON(http.StatusOK, result)
		} else {
			return c.JSON(http.StatusInternalServerError, err.Error())
		}
	}
}

func CreateTask(db *mongo.Collection) echo.HandlerFunc {
	return func(c echo.Context) error {
		var body models.TaskRequestBody
		if err := c.Bind(&body); err != nil {
			return c.JSON(http.StatusBadRequest, err.Error())
		}
		result, err := models.CreateTask(db, body)
		if err != nil {
			return c.JSON(http.StatusInternalServerError, err.Error())
		}
		// result 会返回一个 InsertedID 结构
		return c.JSON(http.StatusOK, result)
	}
}

func UpdateTask(db *mongo.Collection) echo.HandlerFunc {
	return func(c echo.Context) error {
		id := c.Param("id")
		var body models.TaskRequestBody
		if err := c.Bind(&body); err != nil {
			return c.JSON(http.StatusBadRequest, err.Error())
		}
		result, err := models.UpdateTask(db, id, body)
		if err != nil {
			return c.JSON(http.StatusInternalServerError, err.Error())
		}
		return c.JSON(http.StatusOK, result)
	}
}

func DeleteTask(db *mongo.Collection) echo.HandlerFunc {
	return func(c echo.Context) error {
		id := c.Param("id")
		result, err := models.DeleteTask(db, id)
		if err != nil {
			return c.JSON(http.StatusInternalServerError, err.Error())
		}
		return c.JSON(http.StatusOK, result)
	}
}
