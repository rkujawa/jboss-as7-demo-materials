package net.c0ff33.jbossutils

import org.jboss.dmr.ModelNode

/**
 * @author rkujawa
 */
class JBossMemoryNagiosPlugin extends JBossAbstractNagiosPlugin {

    int usedMbWarn
    int usedMbCrit

    @Override
    int check() {
        long usedHeap = getUsedHeap()/1024/1024
        int rv

        print("JBoss Heap ")

        if (usedHeap >= usedMbCrit) {
            print("CRITICAL")
            rv = NAGIOS_RETURN_CRITICAL
        } else if (usedHeap >= usedMbWarn) {
            print("WARNING")
            rv = NAGIOS_RETURN_WARNING
        } else {
            print("OK")
            rv = NAGIOS_RETURN_OK
        }

        println(": " + ((int) usedHeap) + "MB used")

        return rv
    }

    /**
     * Get amount of memory used on heap.
     * @return MB of used memory.
     */
    private long getUsedHeap() {
        ModelNode op
        ModelNode addr
        op = new ModelNode()
        op.get("operation").set("read-resource")
        op.get("include-runtime").set(true)
        addr = op.get("address")
        addr.add("core-service", "platform-mbean")
        addr.add("type", "memory")
        ModelNode rv = null
        try {
            rv = super.client.execute(op)
        } catch (Exception e) {
            e.printStackTrace()
        }
        return rv.get("result").get("heap-memory-usage").get("used").asLong()
    }

}
