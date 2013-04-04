package net.c0ff33.jbossutils

import org.jboss.as.controller.client.ModelControllerClient
import org.jboss.as.controller.client.helpers.standalone.*

import static org.jboss.as.controller.client.helpers.standalone.ServerUpdateActionResult.Result

/**
 * Deploy stuff remotely on JBoss AS 7.
 * @author rkujawa
 * Heavily inspired by examples provided by James R. Perkins.
 */
class JBossCustomRemoteDeployer {

    public final static int DEPLOYMENT_STATUS_SUCCESS = 1
    public final static int DEPLOYMENT_STATUS_FAILURE = 0

    public final static int DEPLOYMENT_TYPE_DEPLOY = 0
    public final static int DEPLOYMENT_TYPE_UNDEPLOY = 1
    public final static int DEPLOYMENT_TYPE_REDEPLOY = 2

    private final ServerDeploymentManager manager
    private final DeploymentPlanBuilder builder
    private DeploymentPlan plan
    private ServerDeploymentPlanResult result

    JBossCustomRemoteDeployer(ModelControllerClient client) {
        this.manager = ServerDeploymentManager.Factory.create(client)
        this.builder = manager.newDeploymentPlan()
    }

    public int run(File file, int deploymentType) {
        if (!file.isFile() || file.size() == 0)
            return DEPLOYMENT_STATUS_FAILURE /* XXX: print some message... */

        switch(deploymentType) {
            case DEPLOYMENT_TYPE_DEPLOY:
                plan = builder.add(file).deploy(file.getName()).build()
                break
            case DEPLOYMENT_TYPE_REDEPLOY:
                plan = builder.replace(file).deploy(file.getName()).build()
                break
            case DEPLOYMENT_TYPE_UNDEPLOY:
                plan = builder.undeploy(file.getName()).remove(file.getName()).build()
                break
            default:
                /* shouldn't happen, throw some exception etc. */
                break
        }

        if (plan.getDeploymentActions().size() > 0) {
            println("foo")
            result = manager.execute(plan).get() // try, catch...
            println("foo2")
            for(DeploymentAction action : plan.getDeploymentActions()) {
                final ServerUpdateActionResult.Result actionResult
                actionResult = result.getDeploymentActionResult(action.getId()).getResult()
                switch(actionResult) {
                    case Result.FAILED:
                    case Result.NOT_EXECUTED:
                    case Result.ROLLED_BACK:
                        println("Something went wrong!")
                        return DEPLOYMENT_STATUS_FAILURE;
                    case Result.CONFIGURATION_MODIFIED_REQUIRES_RESTART:
                        println("The server should be restarted!")
                }
            }
        }
        return DEPLOYMENT_STATUS_SUCCESS
    }
}
