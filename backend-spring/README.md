### JPA/Hibernate, Mapping Types
https://medium.com/@saeiddrv/jpa-hibernate-mapping-types-891686bc6cfd

# Export Inserts
```bash 
    pg_dump --table=ct_factura_detalles --data-only --column-inserts test > detalle.sql
```

# RUN App
mvn spring-boot:run

### AWS Cli
> aws configure

RDS Postgres 

Test Environment
--- test-fact-signer-db
-> DB INSTANCE:  test-fact-signer-db
-> PORT: 5432
-> DB NAME: test_fact_signer_db
-> Endpoint: test-fact-signer-db.ctxyl6hnm9oa.us-east-1.rds.amazonaws.com

# AWS JDBC Wrapper
psql -h test-fact-signer-db.ctxyl6hnm9oa.us-east-1.rds.amazonaws.com -p 5432 -U postgres -d test_fact_signer_db
https://github.com/awslabs/aws-advanced-jdbc-wrapper/blob/main/examples/SpringHibernateExample/README.md


# Docker

% docker images
REPOSITORY          TAG       IMAGE ID       CREATED              SIZE
corretto11+alpine   latest    b961c06ff09f   About a minute ago   274MB
corretto11          latest    b06f854b11f7   6 minutes ago        449MB