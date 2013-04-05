#!/bin/bash
# bash wrapper script for JBoss 7 Groovy-based Nagios plugin

if [ ! -z `which groovy` ] ; then
    GROOVY=`which groovy`
elif [ -x "$GROOVY_HOME/bin/groovy" ] ; then
    GROOVY=$GROOVY_HOME/bin/groovy
elif [ -x "/opt/groovy/bin/groovy" ] ; then
    GROOVY=/opt/groovy/bin/groovy
else
    echo Include groovy in PATH or set GROOVY_HOME or install groovy in /opt/groovy.
fi

JAVA_OPTS="-Djava.util.logging.manager=java.util.logging.LogManager -Djava.util.logging.config.file=logging.properties"

$GROOVY $JAVA_OPTS ./jboss-monitor-jdbc-pool.groovy $@
