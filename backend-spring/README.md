### JPA/Hibernate, Mapping Types
 - https://medium.com/@saeiddrv/jpa-hibernate-mapping-types-891686bc6cfd
 - https://www.callicoder.com/hibernate-spring-boot-jpa-element-collection-demo/

### Security & JWT Token
 - https://www.appsdeveloperblog.com/add-and-validate-custom-claims-in-jwt/#google_vignette
 - https://www.javainuse.com/spring/boot-jwt
 - https://www.toptal.com/spring/spring-security-tutorial
 - https://www.baeldung.com/spring-boot-swagger-jwt


# Export Inserts
```bash 
    pg_dump --table=ct_factura_detalles --data-only --column-inserts test > detalle.sql
```

# RUN App
```bash
    mvn spring-boot:run
    mvn spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=prod
    mvn spring-boot:run -Dspring-boot.run.arguments=--spring.profiles.active=
```

### AWS Cli
> aws configure

### RDS Postgres 

Test Environment
--- test-fact-signer-db
-> DB INSTANCE: test-fact-signer-db
-> PORT: 5432
-> DB NAME: test_fact_signer_db
-> Endpoint: test-fact-signer-db.ctxyl6hnm9oa.us-east-1.rds.amazonaws.com

# AWS JDBC Wrapper
psql -h test-fact-signer-db.ctxyl6hnm9oa.us-east-1.rds.amazonaws.com -p 5432 -U postgres -d test_fact_signer_db
https://github.com/awslabs/aws-advanced-jdbc-wrapper/blob/main/examples/SpringHibernateExample/README.md

# Docker
  - https://docs.aws.amazon.com/corretto/latest/corretto-11-ug/docker-install.html
  - Compile & Build: `mvn clean package && docker build -t fact-signer .`
  - Build the new image `docker build -t fact-signer .` 
  - Run the new image. `docker run -dp 8081:8081 fact-signer`
  - Run It: `docker run -it -p 8081:8081 fact-signer`
  


  - ECR / ViewPush commands.
    1. Authentication token and authenticate your Docker client
    2. Build your Docker image
    3. Tag your image
    4. Push this image

% docker images
    REPOSITORY          TAG       IMAGE ID       CREATED              SIZE
    corretto11+alpine   latest    b961c06ff09f   About a minute ago   274MB
    corretto11          latest    b06f854b11f7   6 minutes ago        449MB
