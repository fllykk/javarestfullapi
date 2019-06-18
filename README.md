# JAVA RESTfull API using wildfly 16 + Resteasy + MySQL | Postgres
//TODO: Tests and Frontend  

 - Install Wildfly
     - Install [PostgreSQL JDBC Driver](https://github.com/pgjdbc/pgjdbc) 
         - create dir cp postgresql-jdbc-42.2-5.jar to modules/org/postgresql/
         - vim modules/org/postgresql/main/module.xml
         ```
         <?xml version='1.0' encoding='UTF-8'?>
         <module xmlns="urn:jboss:module:1.5" name="org.postgresql" slot="main">
             <resources>
                 <resource-root path="postgresql-42.2.5.jar"/>
             </resources>
             <dependencies>
                 <module name="javax.api"/>
                 <module name="javax.transaction.api"/>
             </dependencies>
         </module>
         ```
     
     - Install [MySQL JDBC Driver](https://dev.mysql.com/downloads/connector/j/) 
             - create dir cp mysql-connector-java-8.0.16.jar to modules/org/mysql/
             - vim modules/org/mysql/main/module.xml
             ```
             <?xml version='1.0' encoding='UTF-8'?>
             <module xmlns="urn:jboss:module:1.5" name="org.postgresql" slot="main">
                 <resources>
                     <resource-root path="postgresql-42.2.5.jar"/>
                 </resources>
                 <dependencies>
                     <module name="javax.api"/>
                     <module name="javax.transaction.api"/>
                 </dependencies>
             </module>
             ```
     
     - Add Datasources
         - copy/paste standalone-full.xml and rename it to standalone-full-mysql-pg.xml  
         - vim standalone-full-mysql-pg.xml
         ```
          <datasources>
                         <datasource jndi-name="java:jboss/datasources/ExampleDS" pool-name="ExampleDS" enabled="true" use-java-context="true" statistics-enabled="${wildfly.datasources.statistics-enabled:${wildfly.statistics-enabled:false}}">
                             <connection-url>jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE</connection-url>
                             <driver>h2</driver>
                             <security>
                                 <user-name>sa</user-name>
                                 <password>sa</password>
                             </security>
                         </datasource>
                         <xa-datasource jndi-name="java:jboss/datasources/javateste" pool-name="javateste" enabled="true" use-ccm="true">
                             <xa-datasource-property name="url">
                                 jdbc:mysql://mysqlserverip:3306/javateste?rewriteBatchedStatements=true&amp;autoReconnect=true&amp;characterEncoding=utf8&amp;useUnicode=true
                             </xa-datasource-property>
                             <driver>mysql</driver>
                             <xa-pool>
                                 <min-pool-size>8</min-pool-size>
                                 <max-pool-size>32</max-pool-size>
                                 <use-strict-min>true</use-strict-min>
                                 <is-same-rm-override>false</is-same-rm-override>
                                 <interleaving>false</interleaving>
                                 <pad-xid>false</pad-xid>
                                 <wrap-xa-resource>false</wrap-xa-resource>
                             </xa-pool>
                             <security>
                                 <user-name>mysqlusername</user-name>
                                 <password>mysqlpasswd</password>
                             </security>
                             <validation>
                                 <validate-on-match>false</validate-on-match>
                                 <background-validation>false</background-validation>
                             </validation>
                             <timeout>
                                 <set-tx-query-timeout>false</set-tx-query-timeout>
                                 <blocking-timeout-millis>0</blocking-timeout-millis>
                                 <idle-timeout-minutes>0</idle-timeout-minutes>
                                 <query-timeout>0</query-timeout>
                                 <use-try-lock>0</use-try-lock>
                                 <allocation-retry>0</allocation-retry>
                                 <allocation-retry-wait-millis>0</allocation-retry-wait-millis>
                                 <xa-resource-timeout>0</xa-resource-timeout>
                             </timeout>
                             <statement>
                                 <prepared-statement-cache-size>100</prepared-statement-cache-size>
                                 <share-prepared-statements>false</share-prepared-statements>
                             </statement>
                         </xa-datasource>
                         <xa-datasource jndi-name="java:jboss/datasources/PostgresXADS" pool-name="PostgresXADS" enabled="true" use-ccm="true">
                             <xa-datasource-property name="url">
                                 jdbc:postgresql://postgresqlserverip:5432/ibase
                             </xa-datasource-property>
                             <driver>postgresql</driver>
                             <xa-pool>
                                 <min-pool-size>8</min-pool-size>
                                 <max-pool-size>32</max-pool-size>
                                 <use-strict-min>true</use-strict-min>
                                 <is-same-rm-override>false</is-same-rm-override>
                                 <interleaving>false</interleaving>
                                 <pad-xid>false</pad-xid>
                                 <wrap-xa-resource>false</wrap-xa-resource>
                             </xa-pool>
                             <security>
                                 <user-name>postgres</user-name>
                                 <password>postgres</password>
                             </security>
                             <validation>
                                 <validate-on-match>false</validate-on-match>
                                 <background-validation>false</background-validation>
                             </validation>
                             <timeout>
                                 <set-tx-query-timeout>false</set-tx-query-timeout>
                                 <blocking-timeout-millis>0</blocking-timeout-millis>
                                 <idle-timeout-minutes>0</idle-timeout-minutes>
                                 <query-timeout>0</query-timeout>
                                 <use-try-lock>0</use-try-lock>
                                 <allocation-retry>0</allocation-retry>
                                 <allocation-retry-wait-millis>0</allocation-retry-wait-millis>
                                 <xa-resource-timeout>0</xa-resource-timeout>
                             </timeout>
                             <statement>
                                 <prepared-statement-cache-size>100</prepared-statement-cache-size>
                                 <share-prepared-statements>false</share-prepared-statements>
                             </statement>
                         </xa-datasource>
                         <drivers>
                             <driver name="postgresql" module="org.postgresql">
                                 <xa-datasource-class>org.postgresql.xa.PGXADataSource</xa-datasource-class>
                             </driver>
                             <driver name="mysql" module="org.mysql">
                                 <xa-datasource-class>com.mysql.cj.jdbc.MysqlXADataSource</xa-datasource-class>
                             </driver>
                             <driver name="h2" module="com.h2database.h2">
                                 <xa-datasource-class>org.h2.jdbcx.JdbcDataSource</xa-datasource-class>
                             </driver>
                         </drivers>
                     </datasources>
         ```
     
     - Run server ./standalone.sh -c standalone-full-mysql-pg.xml
     - go to console   http://127.0.0.1:9990/console/index.html and test mysql/postgres connection
     
     - Run Postgres from Docker
     ```
     docker run --rm -d  
        --name pg11 
        --volume $(PWD)/postgres/data:/var/lib/postgresql/data 
        -p 5432:5432 
        postgres:11.3
     
     # Create database
     docker exec -it pg11 /bin/bash
     psql -U postgres
     \?
     \l # list databases
     \dt # list tables
     \d # list table cols
     \c # join database
     CREATE DATABASE ibase
     \c ibase  
     \q  
     ```
     
     
     
     
    