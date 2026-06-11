# API 文档

基础路径：`/api`

认证方式：调用 `POST /api/user/login` 后，服务端通过 session 保存登录状态。后续请求需要携带同一个 session cookie。

返回约定：
- 操作成功通常返回 `true`，失败或无权限通常返回 `false`。
- 查询无权限通常返回 `null`。
- 新增成功通常返回新增记录的 `id`。
- 请求和响应 JSON 字段使用 snake_case。

## 用户

### 登录

`POST /api/user/login`

请求体：

```json
{
  "id": 2,
  "password": "bbjcz"
}
```

返回：`true` 或 `false`。

登录成功后 session 中会保存用户 id 和是否管理员。

### 登出

`POST /api/user/logout`

返回：空。

### 查询全部用户

`GET /api/user/all`

权限：管理员。

返回：

```json
[
  {
    "id": 1,
    "role": "student",
    "student_id": 1,
    "teacher_id": null
  }
]
```

### 新增用户

`POST /api/user`

权限：管理员。

请求体示例：

```json
{
  "role": "student",
  "student_id": 1,
  "teacher_id": null
}
```

教师用户：

```json
{
  "role": "teacher",
  "student_id": null,
  "teacher_id": 1
}
```

管理员用户：

```json
{
  "role": "admin",
  "student_id": null,
  "teacher_id": null
}
```

返回：新增用户 id。默认密码为 `password`。

### 修改自己的密码

`PUT /api/user/password`

权限：已登录。

请求体：

```json
{
  "old_password": "old password",
  "new_password": "new password"
}
```

返回：`true` 或 `false`。旧密码正确且新密码非空时修改成功。

### 删除非管理员用户

`DELETE /api/user/{id}`

权限：管理员。

返回：`true` 或 `false`。只能删除 `role` 不是 `admin` 的用户；目标用户不存在或为管理员时返回 `false`。

## 当前用户

### 查询当前用户信息

`GET /api/me`

权限：已登录。

返回：

```json
{
  "uid": 1,
  "id": 1,
  "name": "kobe",
  "role": "student"
}
```

### 查询当前学生成绩

`GET /api/me/score`

权限：已登录。学生用户返回成绩列表；非学生通常为空。

返回：

```json
{
  "student_score_list": [
    {
      "course_name": "it",
      "teacher_name": "xf",
      "point": 2,
      "score": 88
    }
  ],
  "average_score": 88.0
}
```

## 学生管理

### 查询全部学生

`GET /api/student/all`

权限：管理员。

返回：

```json
[
  {
    "id": 1,
    "name": "kobe"
  }
]
```

### 新增学生

`POST /api/student`

权限：管理员。

请求体：

```json
{
  "name": "new student"
}
```

返回：新增学生 id。

### 修改学生

`PUT /api/student/{old_id}`

权限：管理员。

请求体：

```json
{
  "id": 1,
  "name": "new name"
}
```

返回：`true` 或 `false`。

### 删除学生

`DELETE /api/student/{id}`

权限：管理员。

返回：`true` 或 `false`。

## 教师管理

### 查询全部教师

`GET /api/teacher/all`

权限：管理员。

返回：

```json
[
  {
    "id": 1,
    "name": "xf"
  }
]
```

### 新增教师

`POST /api/teacher`

权限：管理员。

请求体：

```json
{
  "name": "new teacher"
}
```

返回：新增教师 id。

### 修改教师

`PUT /api/teacher/{old_id}`

权限：管理员。

请求体：

```json
{
  "id": 1,
  "name": "new name"
}
```

返回：`true` 或 `false`。

### 删除教师

`DELETE /api/teacher/{id}`

权限：管理员。

返回：`true` 或 `false`。

## 课程管理

### 查询全部课程

`GET /api/course/all`

权限：管理员。

返回：

```json
[
  {
    "id": 1,
    "name": "it",
    "point": 2
  }
]
```

### 新增课程

`POST /api/course`

权限：管理员。

请求体：

```json
{
  "name": "database",
  "point": 2
}
```

返回：新增课程 id。

### 修改课程

`PUT /api/course/{old_id}`

权限：管理员。

请求体：

```json
{
  "id": 1,
  "name": "database",
  "point": 3
}
```

返回：`true` 或 `false`。

### 删除课程

`DELETE /api/course/{id}`

权限：管理员。

返回：`true` 或 `false`。如果课程仍被班级引用，数据库会拒绝删除并返回 `false`。

## 班级管理与教师班级

### 管理员查询全部班级

`GET /api/class/all`

权限：管理员。

返回：

```json
[
  {
    "id": 1,
    "course_id": 1,
    "teacher_id": 1
  }
]
```

### 管理员新增班级

`POST /api/class`

权限：管理员。

请求体：

```json
{
  "course_id": 1,
  "teacher_id": 1
}
```

返回：新增班级 id。

### 管理员修改班级

`PUT /api/class/{old_id}`

权限：管理员。

请求体：

```json
{
  "id": 1,
  "course_id": 1,
  "teacher_id": 1
}
```

返回：`true` 或 `false`。

### 管理员删除班级

`DELETE /api/class/{id}`

权限：管理员。

返回：`true` 或 `false`。如果班级仍被选课或成绩日志引用，数据库会拒绝删除并返回 `false`。

### 教师查询自己的班级

`GET /api/class`

权限：已登录教师。

返回：

```json
[
  {
    "class_id": "1",
    "course_name": "it"
  }
]
```

### 教师查询班级详情

`GET /api/class/{classId}`

权限：已登录教师，且该班级属于当前教师。

返回：

```json
{
  "class_info": {
    "course_name": "it",
    "average_score": 88.0
  },
  "class_score_list": [
    {
      "student_id": 1,
      "student_name": "kobe",
      "score": 88
    }
  ]
}
```

### 教师修改学生成绩

`PUT /api/class/{classId}/student/{studentId}/score`

权限：已登录教师，且该班级属于当前教师。

请求体为数字或 `null`：

```json
88
```

返回：`true` 或 `false`。

## 选课

### 学生选课

`POST /api/enrollment`

权限：已登录学生。

请求体为班级 id：

```json
1
```

返回：`true` 或 `false`。

### 学生退课

`DELETE /api/enrollment/{classId}`

权限：已登录学生。

返回：`true` 或 `false`。只有成绩为 `null` 的选课记录可以退课。

### 查询当前学生可选班级

`GET /api/enrollment/available`

权限：已登录学生。

返回：

```json
[1, 2, 3]
```

## 日志

### 查询成绩修改日志

`GET /api/log/score`

权限：已登录。

返回：

```json
[
  {
    "id": 1,
    "student_id": 1,
    "class_id": 1,
    "new_score": 88,
    "update_time": "2026-06-02T16:00:00"
  }
]
```

## 2026-06-08 API 变更说明

以下内容是相对于上文原 API 文档的追加变更说明，原有接口路径不变。

### 新增学生接口返回值变更

`POST /api/student`

请求体不变：

```json
{
  "name": "new student"
}
```

原返回值：新增学生 id。

现变更为：学生创建成功后，系统会自动创建对应的学生用户，默认密码仍为 `password`。

新返回值：

```json
{
  "user_id": 10,
  "student_id": 1
}
```

### 新增教师接口返回值变更

`POST /api/teacher`

请求体不变：

```json
{
  "name": "new teacher"
}
```

原返回值：新增教师 id。

现变更为：教师创建成功后，系统会自动创建对应的教师用户，默认密码仍为 `password`。

新返回值：

```json
{
  "user_id": 11,
  "teacher_id": 1
}
```

### 管理员查询全部班级返回值变更

`GET /api/class/all`

原返回值只包含班级 id、课程 id、教师 id：

```json
[
  {
    "id": 1,
    "course_id": 1,
    "teacher_id": 1
  }
]
```

现返回值增加课程名和教师名：

```json
[
  {
    "id": 1,
    "course_id": 1,
    "course_name": "it",
    "teacher_id": 1,
    "teacher_name": "xf"
  }
]
```

### 学生查询可选课时返回值变更

`GET /api/enrollment/available`

原返回值只包含可选班级 id：

```json
[1, 2, 3]
```

现返回值增加课程名和教师名：

```json
[
  {
    "class_id": 1,
    "course_name": "it",
    "teacher_name": "xf"
  }
]
```

## 2026-06-11 API 变更说明

### 查询当前学生成绩返回值变更

`GET /api/me/score`

原返回值中的每条课程成绩不包含班级 id。

现返回值中的每条课程成绩增加 `class_id`：

```json
{
  "student_score_list": [
    {
      "class_id": 1,
      "course_name": "it",
      "teacher_name": "xf",
      "point": 2,
      "score": 88
    }
  ],
  "average_score": 88.0
}
```
