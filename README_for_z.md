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

mvn compile quarkus:dev -f api2-web/pom.xml -DskipTests "-Dquarkus.log.level=DEBUG"

.\mvnw compile quarkus:dev -f api2-web/pom.xml -DskipTests

## 问题

- 项目启动时长达35秒，只追踪到 io.quarkus.hibernate.orm.runtime.schema.SchemaManagementIntegrator.runPostBootValidation
  + 原因是 cn.valuetodays.api2.basic.component.NatsConsumer#onStartup()里循环了30秒！！！！
