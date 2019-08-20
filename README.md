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
