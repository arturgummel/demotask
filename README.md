# Java Home task

Task is to build a little Web Application.

### What is done?

  - the user logins with a user/pass combination
  - if the username/password is incorrect, it will be reported with error messages
  - on the signed in page can be found profile form with the user data (first,- last names, birth date, address)
  - the form gives the opportunity to update the data
  - if the data is incorrect, it will be reported with error messages, the correct data will be updated
  - logout button redirects to home page
  - with admin user it is possible to see all user data
  - username - password:
    * admin user:
        * admin - admin
    * ordinary users:
        * user1 - 12345
        * user2 - 12345
        * user3 - 12345
  - there are implemented 3 JUnit tests:
    * matching admin data
    * matching user data
    * matching birth date format
  - there are implemented 3 Selenium tests:
    * trying to sign in and match first,- last names
    * trying to sign in and update the data, then trying to insert incorrect data to catch error messages
    * testing admin table

# Installation

Installation is pretty simple, just need to run:
```sh
$ gradle bootRun
```

after build it will be possible to open Web Application:
```sh
http://localhost:8080
```

To check database go to:
```sh
http://localhost:8080/h2
```

To run tests just write:
```sh
$ gradle test
```