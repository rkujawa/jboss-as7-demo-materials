Add parameter to JVM:
-XX:+HeapDumpOnOutOfMemoryError

or use jmap utility:
$ jmap -dump:file=heap.bin $JVM_PID

Analyze using IBM HeapAnalyzer:
ftp://public.dhe.ibm.com/software/websphere/appserv/support/tools/HeapAnalyzer/ha443.jar
