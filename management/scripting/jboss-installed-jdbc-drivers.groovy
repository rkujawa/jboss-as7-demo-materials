import net.c0ff33.jbossutils.JBossTrivialCallbackHandler
import org.jboss.as.controller.client.ModelControllerClient
import org.jboss.dmr.ModelNode

import javax.security.auth.callback.CallbackHandler

/**
 * Obtain a list of installed JDBC drivers.
 * @author rkujawa
 */

hostname = "vtestjb1.home.c0ff33.net"
port = 9999
login = "admin"
password = "AwfyoxAiHa"

ch = (CallbackHandler) new JBossTrivialCallbackHandler(login, password)

client = ModelControllerClient.Factory.create(hostname, port, ch)

op = new ModelNode();
//op.get("operation").set("read-resource")
op.get("operation").set("installed-drivers-list")
/*
 * include-runtime - citing AS7 Admin Guide: whether to include runtime attributes
 * (i.e. those whose value does not come from the persistent configuration) in the response
 */
//op.get("include-runtime").set(true)
addr = op.get("address")
addr.add("subsystem", "datasources")

println "foo"

retval = client.execute(op)

println(retval)

client.close()
