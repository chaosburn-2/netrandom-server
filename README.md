# netrandom-server
IT361 Project - Server
This is a REST server component created for a project in IT361.

The server component is intended to run on a Glassfish server, compiles with Java 11, and provides only three API interfaces for GET, POST, and DELETE.
Server functionality is to provide a random number following a specific request format. The server then retains the original request and the number generated.

POST queries for the random number. It accepts a properly formatted string in the body of the request. Since the project emulates rolling dice, it follows well established roleplaying game conventions for the random number string. Most strings will consist of three components, a number, the letter 'D', and another number. Each field should have a single space placed between them. The first number represents the quantity of "dice" to be rolled. The second number indicates the number of sides on the die(dice) that are rolled. So "1 D 10" will generate a random number between 1 and 10. "2 D 10" will generate two random numbers between 1 and 10, add them together, then return that number.

GET will return the list of request/number pairs in JSON format.

DELETE will clear the list on the server.
