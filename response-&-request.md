# Request

Our request represents the everything we deemed necessary to find a command, the elements consist of the method ( `GET`, `POST`, etc ) and the path. 
It has only one class - `Request.java`

# Response

This application supports two Responses : 
* `HttpResponse` - sends the response via http , supporting redirect to other html pages
* `ConsoleResponse` - simple response that can be sent either to Console display or to a specific file 

Both these responses share the common interface `IResponse`
