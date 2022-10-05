
# TODO Backend Application

A backend application to demonstrate TODO application APIs


## Authors

- [@prakashkkml](https://github.com/prakashkkml)


## Supported Operations

#### Get all tasks

```http
  GET /tasks
```

#### Get task

```http
  GET /tasks/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Id of item to fetch |

#### Update task

```http
  PUT /tasks/${id}
```

| Request body | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `long` | **Required**. Id of task to update |
| `description`| `string` | **Required**. description of the task to update |
| `priority`      | `int` | priority of the task to update|

#### Create task

```http
  POST /tasks
```

| Request body | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `description`| `string` | **Required**. description of the task |
| `priority`      | `int` | priority of the task|

#### Delete a task

```http
  DELETE /tasks/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Id of task to delete |

## Demo Live

https://todo-application-apis.herokuapp.com/swagger-ui/index.html

