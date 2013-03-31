Three preferred steps to add the data source in JBoss AS 7

1. Define driver as module, by copying it into 
$JBOSS\_HOME/modules/com/example/jdbc/main/
and createing module.xml in the same directory

2. Register driver in JBoss 
- Using CLI: see add-driver.cli for example

3. Add data source
- Using web console
OR
- Editing standalone.xml/domain.xml: see datasource.xml for example
OR
- Using CLI
OR
- By using Management API (native / HTTP)

Officially only JDBC4 drivers are supported.

Using hot deployment scanner to deloy data source is supported only for developer configurations (not on production machines).

It is also possible to avoid creating module, however it only works on standalone configurations. Therefore, it is always advised to use modules for JDBC drivers.
