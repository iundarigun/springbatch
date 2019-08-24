# Spring Batch sample

### Postgres database

To up a Postgres container, run the next instruction:
```
$ docker run --name local-postgres -p 5432:5432 -e POSTGRES_PASSWORD=postgres -d postgres
``` 

To connect to postgres and create a database:

```
$ docker exec -it local-postgres psql -U postgres
# create database devcave_springbatch;
# \q      
```

To run docker if it was create:

```
$ docker start local-postgres
```

### Spring batch

Spring JPA doesn't create the spring batch tables, só you need to create the tables and sequences running the `springbatchtables.sql` file on database.

Adicionally, you can use spring batch tables in memory. In this case the table creation is not necessary 

@StepScope defines the scope of the bean (the same as @Scope("step")) 

### Jobs description:
- ImportEmployee - post: the job imports data from a file in the next format:
    ```csv
    id;name;position;startDate;endDate
    ;John Snow;MANAGER;2015-09-21;
    21;Stephanie Smith;DEVELOPER;2012-08-15;2016-02-05
    ```
- ExportEmployee - get: the job uses spring data to export enabled employees.


## References

- https://medium.com/dev-cave/hello-spring-batch-b01b0ddaa4f2
- https://itnext.io/batch-processing-large-data-sets-with-spring-boot-and-spring-batch-80b8f8c2411e
- https://github.com/emalock3/spring-batch-example
- https://www.petrikainulainen.net/programming/spring-framework/spring-batch-tutorial-writing-information-to-a-file/
  