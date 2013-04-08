import net.c0ff33.jbossutils.JBossTrivialCallbackHandler
import org.jboss.as.controller.client.ModelControllerClient
import org.jboss.dmr.ModelNode

import javax.security.auth.callback.CallbackHandler

/**
 * Obtain a list of installed JDBC drivers.
 * @author rkujawa
 */

hostname = "192.168.200.41"
port = 9999
login = "admin"
password = "jboss"

ch = (CallbackHandler) new JBossTrivialCallbackHandler(login, password)

client = ModelControllerClient.Factory.create(hostname, port, ch)

op = new ModelNode();
op.get("operation").set("installed-drivers-list")

addr = op.get("address")
addr.add("subsystem", "datasources")

retval = client.execute(op)

println(retval)

client.close()
