Every function in this application is implemented in this package : 

## Command Class
Generic class that represents the backbone of every command in this application.

Every command must implement the following method 
* `void execute(ExecutionContext executionContext, IResponse response)`

That given the context `ExecutionContext` in which the command will work upon, replies with a [response](https://github.com/isel-leic-ls/1617-2-LI41D-G10/wiki/response-&-request) `IResponse` through an out parameter.

In addition we created an extra method `String[] translateSemester(String semAcr)` which cannot be overridden by subclasses, that converts a semester into an array containing it's values separated which is used by several commands.

## Commands available in the application
Each command executes the query required to satisfy the request and associates to the response the corresponding views.

The commands are divided by sub-packages that correspond to domain class for each functionality.

### course management

* [GET /courses](#get-courses) - get all courses
* [GET /courses/{acr}](#get-coursesacr) - get a course
* [POST /courses](#post-courses) - post a course

### class management

* [GET /courses/{acr}/classes](#get-coursesacrclasses) - get all classes of a course
* [GET /courses/{acr}/classes/{sem}](#get-coursesacrclassessem) - get classes of a course on a semester
* [GET /courses/{acr}/classes/{sem}/{num}](#get-coursesacrclassessemnum) - get a class of a course on a semester
* [POST /courses/{acr}/classes](#post-coursesacrclasses) - post a class onto a course

### programme management

* [GET /programmes/{pid}](#get-programmespid) - get a programme
* [GET /programmes/{pid}/courses](#get-programmespidcourses) - get courses of a programme
* [GET /programmes](#get-programmes) - get a programme
* [POST /programmes](#post-programmes) - post a programme
* [POST /programmes/{pid}/courses](#post-programmespidcourses) - post a course onto a programme

### student management

* [GET /courses/{acr}/classes/{sem}/{num}/students](#get-coursesacrclassessemnumstudents) - get students of a class
* [GET /courses/{acr}/classes/{sem}/{num}/students/sorted](#get-coursesacrclassessemnumstudentssorted) - get ordered students of a class
* [POST /courses/{acr}/classes/{sem}/{num}/students](#post-coursesacrclassessemnumstudents) - post student to class
* [DELETE /courses/{acr}/classes/{sem}/{num}/students/{numStu}](#delete-coursesacrclassessemnumstudentsnumstu) - delete student from class

### teacher management

* [GET /teachers/{num}/classes](#get-teachersnumclasses) - get classes of teacher
* [GET /courses/{acr}/classes/{sem}/{num}/teachers](#get-coursesacrclassessemnumteachers) - get teachers of class
* [POST /courses/{acr}/classes/{sem}/{num}/teachers](#post-coursesacrclassessemnumteachers) - post teacher to a class

### user management

* [GET /students](#get-students) - get all students
* [GET /teachers](#get-teachers) - get all teachers
* [GET /students/{num}](#get-studentsnum) - get a student
* [GET /teachers/{num}](#get-teachersnum) - get a teacher
* [GET /users](#get-users) - get all users
* [POST /students](#post-students) - post a student
* [POST /teachers](#post-teachers) - post a teacher
* [PUT /students/{num}](#put-studentsnum) - update a student
* [PUT /teachers/{num}](#put-teachersnum) - update a teacher

### functional management

* [EXIT /](#exit-) - exits app
* [LISTEN /](#listen-) - starts server
* [OPTION /](#option-) - lists commands

#### `POST /courses`

creates a new course, given the following parameters
  * `name` - course name.
  * `acr` - course acronym.
  * `teacher` - number of the coordinator teacher.

#### `GET /courses`

shows all courses.

#### `GET /courses/{acr}`

shows the course indentified by the acronym `acr`

#### `POST /courses/{acr}/classes`

creates a new class on the course with `acr` acronym, given the following parameters
* `sem` - semester identifier (e.g. `1415v`).
* `num` - class number - (e.g. `D1`).

#### `GET /courses/{acr}/classes` 

shows all classes for a course of the specified course `acr`.

#### `GET /courses/{acr}/classes/{sem}` 

shows all classes of the `acr` course on the `sem` semester.

#### `GET /courses/{acr}/classes/{sem}/{num}` 

shows the classes of the `acr` course on the `sem` semester and with `num` number.

#### `GET /programmes`

list all the programmes.

#### `POST /programmes` 

creates a new programme, given the following parameters
  * `pid` - programme acronym (e.g. "LEIC").
  * `name` - programme name.
  * `length` - number of semesters.

#### `GET /programmes/{pid}` 

shows the details of programme with `pid` acronym.

#### `POST /programmes/{pid}/courses` 

adds a new course to the programme `pid`, given the following parameters
  * `acr` -  the course acronym.
  * `mandatory` - `true` if the course is mandatory.
  * `semesters` - `comma` separated list of curricular semesters.

#### `GET /programmes/{pid}/courses` 

shows the course structure of programme `pid`.

#### `POST /courses/{acr}/classes/{sem}/{num}/students` 

adds a new student to a class, given the following parameter that can occur multiple times (e.g. `numStu=12345&numStu=54321`).
  * `numStu` - student number.

#### `GET /courses/{acr}/classes/{sem}/{num}/students` 

shows all students of a class in the course `acr` lectured in the semester `sem` with the identifier `num`.

#### `DELETE /courses/{acr}/classes/{sem}/{num}/students/{numStu}` 

removes a student `numStu` from a class in the course `acr` lectured in the semester `sem` with the identifier `num`.

#### `GET /courses/{acr}/classes/{sem}/{num}/students/sorted` 

returns a list with all students of a class in the course `acr` lectured in the semester `sem` with the identifier `num`, ordered by increasing student number.

####  `POST /courses/{acr}/classes/{sem}/{num}/teachers` 

adds a new teacher to a class in the course `acr` lectured in the semester `sem` with the identifier `num`, given the following parameters
  * `numDoc` - teacher number

#### `GET /courses/{acr}/classes/{sem}/{num}/teachers` 

shows all teachers for a class in the course `acr` lectured in the semester `sem` with the identifier `num`.

#### `GET /teachers/{num}/classes` 

shows all classes for the teacher with `num` identifier.

#### `POST /teachers` 

creates a new teacher, given the following parameters
  * `num` - teacher number.
  * `name`- teacher name.
  * `email` - teacher email.
  * Example: `POST /teachers num=1207&name=Pedro+Félix&email=pedrofelix@...`.

#### `POST /students` 

creates a new student, given the following parameters
  * `num` - student number.
  * `name`- student name.
  * `email` - student email.
  * `pid` - programme acronym.

#### `GET /users` 

shows all users.

#### `GET /teachers` 

shows all teachers.

#### `GET /students` 

shows all students.

#### `GET /teachers/{num}` 

shows the teacher with identifier `num`.

#### `GET /students/{num}` 

shows the student with the identifier `num`.

#### `PUT /teachers/{num}` 

updates an existent teacher with identifier `num`.

#### `PUT /students/{num}` 

updates an existent student with identifier `num`.

#### `LISTEN /` 

starts the HTTP server. This command receives a `port` parameter containing the TCP port where the server should listen for requests.

#### `OPTION /` 

presents a list of available commands and their characteristics.

#### `EXIT /` 

ends the application.
