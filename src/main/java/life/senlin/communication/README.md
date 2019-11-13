

```bash
mvn flyway:migrate
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
注意在使用mybatis generator生成mapper时，window系统需要把generatorConfig.xml中的路径中的“/”改为“\”
```