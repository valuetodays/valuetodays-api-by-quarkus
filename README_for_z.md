#

##

Singleton: eager Singleton

ApplicationScoped: lazy Singleton
     
## function list

- [x] rest
- [x] mysql
- [x] redis
- [x] schedule
- [x] spring jpa/properties/web

## quarkus vs micronaut vs springboot

quarkus 的 dev-ui 页面，可以提供很多有用的信息 http://localhost:8080/q/dev-ui/welcome

## 启动

mvn compile quarkus:dev -f api2-web/pom.xml -DskipTests

.\mvnw compile quarkus:dev -f api2-web/pom.xml -DskipTests

