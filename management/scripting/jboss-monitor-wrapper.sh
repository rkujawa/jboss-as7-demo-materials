#!/bin/bash
# bash wrapper script for JBoss 7 Groovy-based Nagios plugin
JBOSS_CLIENT=/opt/jboss-client/

if [ ! -z `which groovy 2> /dev/null` ] ; then
    GROOVY=`which groovy`
elif [ -x "$GROOVY_HOME/bin/groovy" ] ; then
    GROOVY=$GROOVY_HOME/bin/groovy
elif [ -x "/opt/groovy/bin/groovy" ] ; then
    GROOVY=/opt/groovy/bin/groovy
else
    echo Include groovy in PATH or set GROOVY_HOME or install groovy in /opt/groovy.
    exit 2
fi

CLASSPATH=.:`dirname $0`
for i in $JBOSS_CLIENT/*.jar ; do CLASSPATH=$CLASSPATH:$i ; done
export CLASSPATH

JAVA_OPTS="-Djava.util.logging.manager=java.util.logging.LogManager -Djava.util.logging.config.file=logging.properties"

SCRIPTNAME=`echo $0 | sed 's/\.sh/.groovy/'`

$GROOVY $JAVA_OPTS $SCRIPTNAME $@
