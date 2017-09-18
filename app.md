In this package we retain our entry points:
## Console Application
`AcademicActivitiesApp.java` is our console entry point, which has 2 modes: 
     1. an interactive mode that indefinitely waits for a command to be inputted, this is the mode used when the app is booted with no paramters; 
     2. a single command execution mode that closes the app after it's execution, we only enter in this mode when the app boots with parameters.

## Web Application
`HerokuLauncher.java` is the entry point used by the [Heroku Cloud Application Platform](https://www.heroku.com/) which starts the server and essentially executes the same code as the `Listen` command.
In this Web Application , users can navigate through different end points by simple links that result in the structure that is shown in the following image  : 
![Link graph](http://i.imgur.com/PrQfGaL.png)

(Click [here](https://docs.google.com/drawings/d/1gzFou1boKQL3fpCMt5nJRahDaZwu2sK4nhOME8tKmmA/pub?w=1215&h=895) for a bigger picture)

## App usage
To input a command you must follow a set of rules: 

Each command is defined using the following generic structure

```
{method} {path} {headers} {parameters}
```

where
* the `method` defines the type of action to perform (e.g. `GET` or `POST`). It isn't related with the Java method concept.
* the `path` defines the resource on which the command is executed.
* the `parameters` is a sequence of `name=value` pairs, separated by `&` (e.g. `name=LS&description=software+laboratory`).
* the `headers` component is composed by a sequence of `name:value` pairs, where each pair is separated by the '|' character.

Each command allows the response to be supported in different formats : `text/plain`, `application/json` and `text/html` 
~~~
accept:text/plain
~~~

The `GET` methods also support the `file-name` header, defining the file system location for the outputted representation.
If this header is absent, then the representation is written into the standard output.

### Paging ###

All the `GET` commands that return a sequence of items (e.g. `GET  /programmes` and `GET /courses`) support _paging_, i.e., the ability to return a subsequence of the original sequence.
This paging is defined by two parameters (name-value pairs in the `parameters` set):

* `top` - length of the subsequence to return.
* `skip` - start position of the subsequence to return.

Both these parameters are optional.

### Command Prompt ###
This application can be executed via command prompt.
The corresponding command in the command line is: 
```bash
java -cp build\classes\main;vendor\main\* pt.isel.ls.academicActivities.app.AcademicActivitiesApp
```