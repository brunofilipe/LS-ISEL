Package that represents the final representation of the execution of different commands, that can be represented in various formats and used by other applications .

## ErrorView

Generates an html page that gives the info of the error cause and its http status code, this page occurs when an exception is thrown during an execution of a command or of a connection to a database.

## HomeView

Home html page that corresponds to the default path, shows a list of directions to different model entities and gives the information of the developers and context of this application.

# commands

All GET commands have a specific html page for its execution , this means that all commands that return the information of multiple objects (e.g : showing all teachers or students) will be represented by html pages, in other hand the commands that return specific information of a single object (e.g : details of a course ) will be representing by a simple html page detailing the object.

While showing all the info of multiple objects, there is the possibility to insert another object (e.g : inserting a new teacher at the page that shows all teachers) using an html element called `form`, and after its creation, the app returns a status code of 303 (`SeeOther`) and redirects to a page that shows the details of the inserted objects.

# model
Every domain object has a representation in text/plain and application/json, if there is just an object to represent  we represent it via a single view (e.g : `SingleClassView/SingleClassJsonView`), but if we need to represent multiple objects (e.g lists of objects) we use a compose view (e.g : `ClassesView/ClassesJsonView`)


