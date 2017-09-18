This package contains the model which stores the data that is retrieved from the command's execution.
Our domain is divided in six entities:
* Class
* Course
* Programme
* ProgrammeStructure
* Student
* Teacher

while modeling the entities of our entity-relationship model, besides including the corresponding attributes we also took into consideration the relationships between each entity, so each representation if needed has extra attributes depicting those relations.
To simplify the usage of these classes we created an interface `IEntity` that represents any entity on our domain.