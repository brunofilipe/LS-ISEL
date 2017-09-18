Our utils package has all libraries required.

## HTML
The library used by our application is the one provided in the shared repository. We've made some significant changes to facilitate it's usage, namely:
* extra elements to `Html.java`;
* added an extra constructor to `HtmlPage.java` in order to support css;
* all user inputted data passes through `HtmlText.java`, so we now escape all strings to reduce ambiguity in reserved characters (like ", <, >, etc) and avoid injection;
* created a new class `HtmlRaw.java` to input elements with no closing tag.

## HTTP
This library is also provided by the shared repository. We've made small changes to it:
* We altered the `FormsUrlEncoded.java` in order to return the decoded query string untouched instead of returning a map of parameters because that mapping is done by us later in the `Parameters.java` class;
* Added status 409 Conflict to the class `HttpStatusCode.java`.
## JSON
Similar to html library, this library was provided in one of the shared repositories of previous semesters, it consists in json objects, json arrays, and pair key-value.

* `Json.java`- base class for this library; 
* `JsonArray.java` - responsible for writing json array syntax `[json objects...]`;
* `JsonObject.java` - responsible for writing json object syntax `{json pair key-value...}`;
* `JsonPairKeyValue.java` - responsible for pair key value, separated by ':' ;
* `JsonText.java` - specific class to write values in json syntax ;

We took advantage of the class `Writable.java` to use as an interface for our views and created `Pair.java` to use throughout our application.