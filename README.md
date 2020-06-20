# Requirements
The following requirements are needed to run the application:\
PostgreSQL version 12.2 https://www.postgresql.org/download/ \
Java SE version 14 https://www.oracle.com/java/technologies/javase/jdk14-archive-downloads.html \
Git https://git-scm.com/downloads

# Download the project
Clone the project by using a [Git GUI](https://git-scm.com/downloads/guis), integrated GIT system in your IDE, or by using the terminal.
When using the terminal follow the next steps:
- Use the command `cd` to go to the folder where you want the application to be downloaded
- Run ``git clone git@github.com:Medladz/MedTracker-backend.git`` or ``git clone https://github.com/Medladz/MedTracker-backend.git`` if you don't have SSH.

# Database
## Creating the database
If you don't want to use the terminal download [pgAdmin4](https://www.pgadmin.org/download/) and create the database ``medtracker``.\
If postgreSQL won't start follow this [guide](https://tableplus.com/blog/2018/10/how-to-start-stop-restart-postgresql-server.html).\
When using the terminal (SQL shell, which is provided when you have installed postgreSQL) follow the next steps:
- Open the software ``SQL Shell (pgsql)``
- Press enter five times
- Run the following command ``CREATE DATABASE medtracker;``

## Database migration config
The next steps makes it possible to migrate and populate the database:
- Open the project folder and navigate towards ``MedTracker-backend\src\vendors\flyway-db-migrations\conf``
- Copy the file ``flyway_preview.conf`` and rename it to ``flyway.conf``
- Open ``flyway.conf``
- Fill in ``flyway.url=`` with your database URL ``jdbc:postgresql://<host>/<databasename>``. Which is probably something like ``jdbc:postgresql://localhost/medtracker``
- Fill in ``flyway.user=`` with your database username. Which is probably ``postgres``.
- Fill in ``flyway.password=`` with your database password. Which is probably empty.

## Migrating and populating the database
The following steps needs to be done with the terminal:
- Navigate towards ``MedTracker-backend\src\vendors\flyway-db-migrations``
- Run the following command ``./flyway migrate``

# Running the development Server
The following steps needs to be done with the terminal:
- Navigate towards ``MedTracker-backend``
- Run the following command ``./gradlew run``
This can take a while when you are doing this for the first time, because gradle will download every dependency.
