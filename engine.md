This package retains all classes related to the core functionality of the application.

## CommandMatcher

In this class we process every request sent by the web client to our server.

Basically, it does the same job as running the application through the console but the data worked upon is specific to the server.

## ExecutionContext

Contains all the relevant information needed in order to execute a single command, namely its parameters and the connection object to a database.

## Parameters

Receives and stores in a map the parameters, headers and variable paths inputted by the user as a `key=value` pair. 
This class also provides the possibility of accessing the values of the map through its key, these being a list of values or a single value.

## QueryResult

Class that, as the name indicates, contains the result of a database request, it encapsulates either a list or a single result returned by any query of our application.

## RouteInfo

This class was created because the only place where we had the information regarding the corresponding values of variable paths was when we were searching for the command through its path in our command tree. 
It contains a list of the variable paths with their respective value and the command associated with the path.

## Routes

Stores all commands in a tree structure consisting of `CommandNode` objects, each one of them contains a list of children nodes representing the branching commands available from the current node. When one of the nodes has an instance of a command stored in it, then that node represents a command.

To add a new command to the tree, all you need to do is create a new entry in our command list `cmdList` in the method `void fillCmdList()`, this list is made of `Pair` instances containing a path and a command associated with it. The reason why we decided to separate the path from the command was to add extra flexibility to our app, so that we can have two different paths leading to the same command if we choose to do so. 

The next time this class gets instantiated, the private method `void fillCmdTree()` will iterate over the list and create a tree of `CommandNode` based on the path and command found in each entry on the list.

When searching for a command, we expect that the user inputs the path correctly, which means our searching routine will react accordingly if the path is non existent in our tree. Every time a node that represents part of the variable path of a command is found, we immediately store the key/value pair.
 
### CommandNode

This inner class represents a node of our tree, which contains every relevant information to any command available. In order to support a variable path in any full path of a command, each node has, along side a list of "normal" nodes `children`, a special `varChild` which has the key of the variable path, besides containing everything a normal node already has. This will be used to create the key/value pair later.

## UrlTo

Generates different URLs to use in http mode of the application via client. For each application end point, we create an URL to its GET command or POST command.