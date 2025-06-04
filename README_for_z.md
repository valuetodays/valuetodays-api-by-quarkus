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

- [x] 项目启动时长达35秒，只追踪到
  io.quarkus.hibernate.orm.runtime.schema.SchemaManagementIntegrator.runPostBootValidation
  + 原因是 cn.valuetodays.api2.basic.component.NatsConsumer#onStartup()里循环了30秒！！！！
- [x] 解决生成的的like sql中有escape ''导致mysql服务端执行报错：Incorrect arguments to ESCAPE
  + 解决方法是：新写一个Dialect，继承org.hibernate.dialect.MySQLDialect，并重写isNoBackslashEscapesEnabled()并返回true；
  + 排查过程：写一个测试方法cn.valuetodays.api2.web.service.WeworkGroupBatchServiceImplTest.findAll()
  + 现在执行生成的sql时报错；
  + 追踪到 org.hibernate.dialect.MySQLSqlAstTranslator，里面的sqlBuffer就是拼接的sql。在里面搜索escape，找到了
    org.hibernate.sql.ast.spi.AbstractSqlAstTranslator.visitLikePredicate()中的

```java
			appendSql( " like " );
			likePredicate.getPattern().accept( this );
			if ( likePredicate.getEscapeCharacter() != null ) {
				appendSql( " escape " );
				likePredicate.getEscapeCharacter().accept( this );
			}
```

+ 而 likePredicate.getEscapeCharacter() 确实是null，就不会拼接escape，接着按f8跳到了
  org.hibernate.dialect.MySQLSqlAstTranslator.visitLikePredicate()中的

```java
		// Custom implementation because MySQL uses backslash as the default escape character
		if ( getDialect().getVersion().isSameOrAfter( 8, 0, 24 ) ) {
			// From version 8.0.24 we can override this by specifying an empty escape character
			// See https://dev.mysql.com/doc/refman/8.0/en/string-comparison-functions.html#operator_like
			super.visitLikePredicate( likePredicate );
			if ( !getDialect().isNoBackslashEscapesEnabled() && likePredicate.getEscapeCharacter() == null ) {
				appendSql( " escape ''" );
			}
		}
```

+ 发现重写dialect的isNoBackslashEscapesEnabled()
  即可。只能重写一个类继承org.hibernate.dialect.MySQLDialect，因为MySQLDialect中noBackslashEscapesEnabled是构造器赋值的，没有set方法。
+ add BeansProduces to expose bean that can not be injected automatically.
