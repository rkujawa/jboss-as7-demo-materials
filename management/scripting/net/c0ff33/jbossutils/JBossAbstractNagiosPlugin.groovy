package net.c0ff33.jbossutils

import javax.security.auth.callback.CallbackHandler
import org.jboss.as.controller.client.*

/**
 * @author rkujawa
 */
abstract class JBossAbstractNagiosPlugin {

    public final static int NAGIOS_RETURN_OK = 0;
    public final static int NAGIOS_RETURN_WARNING = 1;
    public final static int NAGIOS_RETURN_CRITICAL = 2;

    protected ModelControllerClient client
    private CallbackHandler ch

    public boolean connect(String hostname, int port, String login, String password) {
        ch = (CallbackHandler) new JBossTrivialCallbackHandler(login, password)
        client = ModelControllerClient.Factory.create(hostname, port, ch)

    }

    public void disconnect() {
        client.close()
    }

    public abstract int check();

}
