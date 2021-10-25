
# Reproduce Steps

## Prepare environment

1. Run a new mysql database instance & axon server via docker.

    ```shell
    $ docker-compose up
    ```

2. Create application-local.properties file copying from application-local.properties.sample. Modify the connectionString accordingly.

3. Run or Debug application on http://localhost:8080/swagger-ui.html

## Test case for confused scenario

+ Case 1: A custom CheckedBusinessException
  1. Create an EmptyName person as shown:

        [create-emptyname-person](test/Screenshot-1.png)

  2. Axon will not retry this exception, which is expected behavior.

        [axon-will-not-retry-this-checked-exception](test/Screenshot-2.png)

+ Case 2: A custom CheckedDataAccessException ?
  1. Create a person named 'alice', the person was saved correctly.
    [create-person-with-same-name](test/Screenshot-3.png)
  2. Create the same person again, the UniqueConstraints will make it fail via DataAccessException.
     
     Axon will retry this EventHandler infinitely due to the UnexpectedRollbackException.
     
     [axon-will-retry-this-exception](test/Screenshot-4.png)

### Showcase Folder Structure explaining

```tree
├── main
│   ├── java
│   │   └── info       
│   │       └── cepheus
│   │           └── showcase_axon_exception
│   │               ├── api
│   │               │   └── command
│   │               │       └── person
│   │               │           ├── PersonController.java
│   │               │           └── person-dto.kt        
│   │               ├── application
│   │               │   ├── command
│   │               │   │   └── PersonService.java
│   │               │   └── query
│   │               │       ├── PersonEventHandler.java
│   │               │       └── PersonQueryService.java
│   │               ├── config
│   │               │   ├── CommonsLoggingConfiguration.java
│   │               │   ├── MyAxonErrorHandler.java
│   │               │   ├── ProcessorConfiguration.java
│   │               │   └── SwaggerConfiguration.java
│   │               ├── coreapi
│   │               │   ├── command
│   │               │   │   └── person.kt
│   │               │   ├── event
│   │               │   │   └── person.kt
│   │               │   └── exception
│   │               │       ├── checked-business-exception.kt
│   │               │       └── checked-data-access-exception.kt
│   │               ├── domain
│   │               │   ├── command
│   │               │   │   └── PersonAggregate.java
│   │               │   └── query
│   │               │       ├── personInformation
│   │               │       │   ├── PersonInformation.java
│   │               │       │   └── PersonInformationRepository.java
│   │               │       └── personSnapshot
│   │               │           ├── PersonSnapshot.java
│   │               │           └── PersonSnapshotRepository.java
│   │               ├── infrastructure
│   │               │   ├── PersonQueryServiceImpl.java
│   │               │   └── PersonServiceImpl.java
│   │               └── ShowcaseAxonExceptionApplication.java
│   └── resources
│       ├── application-local.properties
│       ├── application-local.properties.sample
│       ├── application.properties
```

The Api Controller & Dto are located in api/ package.

The Application service & EventHandler are located in application/ package.

The Aggregate/CommandHandler & "Query Entity" are located in domain/ package.

The Service implementation are located in infrastructure/ package.

### Questions here
1. Currently I only know we can skip the problematic token in our query database. But the DataAccessException still cannot be resolved by any code.
Is there a way to tell AxonServer that "I know something wrong happended, and it's not a transient issue, just ignore it"?

