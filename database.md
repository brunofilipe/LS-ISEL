This package contains everything that concerns the database of the application, we have:
* `DatabaseConnection.java` that's responsible for getting the environment variables needed to connect to the database we are using and performing it;
* `DbModel.java` which has methods responsible for instantiating each class of the model, receiving the connection and a `ResultSet` from which comes the data needed to instantiate the specific class.
* a sub-package named `data` which contains a class for each entity in our model with the queries we thought necessary to access our database.
 