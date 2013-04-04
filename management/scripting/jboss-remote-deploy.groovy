import net.c0ff33.jbossutils.JBossCustomRemoteDeployer
import net.c0ff33.jbossutils.JBossTrivialCallbackHandler

import javax.security.auth.callback.CallbackHandler

/**
 * Example script deploying WAR to a remote JBoss AS 7 instance, utilizing JBossCustomRemoteDeployer.
 * @author rkujawa
 */
hostname = "localhost"
port = 9999
login = "adminuser"
password = "secret"

ch = (CallbackHandler) new JBossTrivialCallbackHandler(login, password)

client = org.jboss.as.controller.client.ModelControllerClient.Factory.create(hostname, port, ch)

deployer = new JBossCustomRemoteDeployer(client)
deployer.run(new File("/Users/rkujawa/testApp.war"), JBossCustomRemoteDeployer.DEPLOYMENT_TYPE_DEPLOY)

client.close()
