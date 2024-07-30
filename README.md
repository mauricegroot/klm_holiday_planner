# FastTrack Holidays assignment (backend)

This is a REST-api making use of Spring Boot for registering holidays at KLM. The following endpoints are available.

- GET /holidays - shows all holidays in the system
- POST /holidays - adds a new holiday
- DELTE /holidays/{id} - deletes a specific holiday
- PUT /holidays - updates an existing holiday (to be implemented)

- GET /employees/ - shows all employees
- GET /employees/{id}/holidays - shows all holidays of a specific employee

Employees consists of
- ID (klm123456)
- Name

Holidays consists of
- ID
- Label
- Start date
- End date
- Status
- Employee ID

The following business rules apply:

- There should be a gap of at least 3 working days between holidays.
- A holiday must be planned at least 5 working days before the start date.
- A holiday must be cancelled at least 5 working days before the start date.
- Holidays must not overlap (for the sake of this assignment also not between different crew members).

## Run
The script can be started using

```
$ mvn clean install
$ mvn spring-boot:run
```

The .jar-executable can be started using
```
$ java -jar ./target/assignment-0.0.1-SNAPSHOT.jar
```

By default the application will run on `http://localhost:8080`.
