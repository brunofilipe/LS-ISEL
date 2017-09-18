
This package contains the creation of an HTTP Server through `Jetty Servlet` technology and interaction via `HTTP` protocol (Hypertext Transfer Protocol) to receive command requests and return their responses. 

## HttpServer

Creates an instance of an http server via `Jetty` technology, by associating itself to TCP port where the server should listen for requests and maps the home page (`/`).

## MainServlet

Consists in connecting the web app to outside via HTTP protocol , by GET requests or POST Requests , dispatches the path to command to `CommandMatcher` - class from engine package that consists in getting the instance of the required command , executes it and redirect to its view in specific format .



