# PoC for postgres full text search

This repository contains a simple proof of concept (PoC) for implementing full-text search in PostgreSQL.

Major goals:

- Demonstrate how to set up and use PostgreSQL's full-text search capabilities.
- Provide example queries and configurations for effective text searching.
- Provide examples for query multiple tables with full text search.
- Showcase usage with spring data jpa and hibernate.

## Run it

This projects uses spring support for docker compose, which will spin a postgres container for you.

```bash
./mvnw spring-boot:run
```

## Prerequisites

- java 21
- maven 3.9+
- docker

## Conclusions

PostgreSQL's full-text search is a powerful feature that can significantly enhance the search capabilities
of your applications.
Using Specification, one can still keep spring data capabilities, while leveraging the power of full text search.

Cons:
The introduction of a specific hibernate dialect for full-text search can add complexity to the project, and can
be a maintenance burden.
Multilanguage support needs to be thought in advance, and one new column needs to be added for each language.
Since tsvector needs to be updated on each insert/update, it can add some overhead to the write operations.
