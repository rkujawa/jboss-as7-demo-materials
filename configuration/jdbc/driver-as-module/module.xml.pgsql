<?xml version="1.0" encoding="UTF-8"?>
<module xmlns="urn:jboss:module:1.1" name="org.postgresql">
	<resources>
		<resource-root path="postgresql-9.0-801.jdbc4.jar"/>
	</resources>
	<dependencies>
		<module name="javax.api"/>
		<!-- if using XA DS	<module name="javax.transaction.api"/> -->
	</dependencies>
</module>

