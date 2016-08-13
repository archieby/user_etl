##Random user ETL command line application

Data Source: https://randomuser.me/

Available commands:
* initdb - Initializes DB ([re]creates required tables).
* populate [num] - populates DB with num Users. If num not provided default value 50 is used.
* add <first-name> <last-name> - adds User with the provided name to the DB.
* clean - cleans DB.
* all - retrieves all User data from DB.
* get [id-numeric] - gets the User from DB by id if one exists.
* get [name-string] - gets all Users from DB whoes first or last name matches ignore case this wildcard - *<name-string>*.
* count - returns number of Users in DB.
* help - shows this help.
* exit - exits from the Application.

PostgreSQL schema is stored under the **../main/src/resources/** folder:
* users.sql
* locations.sql

Application configuration: **../main/src/resources/application.conf**

Feel free to modify the provided settings as required.

