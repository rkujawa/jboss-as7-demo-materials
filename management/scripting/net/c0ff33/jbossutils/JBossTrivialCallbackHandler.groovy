package net.c0ff33.jbossutils

import javax.security.auth.callback.Callback
import javax.security.auth.callback.CallbackHandler
import javax.security.auth.callback.NameCallback
import javax.security.auth.callback.PasswordCallback
import javax.security.auth.callback.UnsupportedCallbackException
import javax.security.sasl.RealmCallback


/**
 * Simplistic implementation of security callback handler to use with native JBoss Management API.
 * @author rkujawa
 */
class JBossTrivialCallbackHandler implements CallbackHandler {

    private String username
    private String password
    private String realm
    private boolean useRealm

    JBossTrivialCallbackHandler(String username, String password, String realm) {
        this.username = username
        this.password = password
        this.realm = realm
        useRealm = true;
    }

    JBossTrivialCallbackHandler(String username, String password) {
        this.username = username
        this.password = password
        useRealm = false;
    }

    /**
     * Handle all passed callbacks depending on type.
     * @param callbacks
     * @throws IOException
     * @throws UnsupportedCallbackException
     */
    @Override
    void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        for (Callback c : callbacks) {
            if (c instanceof NameCallback) {
                NameCallback nc = c;
                nc.setName(username)
            }
            if (c instanceof PasswordCallback) {
                PasswordCallback pc = c;
                pc.setPassword(password.toCharArray())
            }
            if (c instanceof RealmCallback) {
                RealmCallback rc = c;
                if (useRealm)
                    rc.setText(realm)
                else
                    rc.setText(rc.getDefaultText())
            }
        }
    }
}
