package net.c0ff33.jbossutils
import org.jboss.dmr.ModelNode;

/**
 * Check percent of JDBC pool connections in use.
 * @author rkujawa
 */
class JBossJdbcPoolNagiosPlugin extends JBossAbstractNagiosPlugin {

    String dsName
    int warnPercent
    int critPercent

    @Override
    int check() {

        int activeCount = getActiveCount()
        int availableCount = getAvailableCount()

        println("Datasource " + dsName + " using " + activeCount + " out of "
            + availableCount + " connections")

        if ((activeCount * 100 / availableCount) >= critPercent)
            return NAGIOS_RETURN_CRITICAL

        if ((activeCount * 100 / availableCount) >= warnPercent)
            return NAGIOS_RETURN_WARNING

        return NAGIOS_RETURN_OK
    }

    private int getAvailableCount() {
        return Integer.parseInt(getDatasourcePoolStatistics().get("AvailableCount").asString())
    }

    private int getActiveCount() {
        return Integer.parseInt(getDatasourcePoolStatistics().get("ActiveCount").asString())
    }

    private ModelNode getDatasourcePoolStatistics() {
        ModelNode op
        ModelNode addr
        op = new ModelNode()
        op.get("operation").set("read-resource")
        op.get("include-runtime").set(true)
        addr = op.get("address")
        addr.add("subsystem", "datasources")
        addr.add("data-source", dsName)
        addr.add("statistics", "pool")
        ModelNode rv = null
        try {
            rv = super.client.execute(op);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rv.get("result")
    }

}
