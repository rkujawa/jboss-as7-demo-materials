import org.codehaus.cargo.container.ContainerType
import org.codehaus.cargo.container.RemoteContainer
import org.codehaus.cargo.container.configuration.Configuration
import org.codehaus.cargo.container.configuration.ConfigurationType
import org.codehaus.cargo.container.deployable.Deployable
import org.codehaus.cargo.container.deployer.Deployer
import org.codehaus.cargo.container.deployer.DeployerType
import org.codehaus.cargo.container.jboss.JBossPropertySet
import org.codehaus.cargo.container.jboss.deployable.JBossWAR
import org.codehaus.cargo.container.property.GeneralPropertySet
import org.codehaus.cargo.container.property.RemotePropertySet
import org.codehaus.cargo.generic.ContainerFactory
import org.codehaus.cargo.generic.DefaultContainerFactory
import org.codehaus.cargo.generic.configuration.ConfigurationFactory
import org.codehaus.cargo.generic.configuration.DefaultConfigurationFactory
import org.codehaus.cargo.generic.deployer.DefaultDeployerFactory
import org.codehaus.cargo.generic.deployer.DeployerFactory

/**
 * Deploy WAR to a remote JBoss 7.1 server using CARGO API.
 * @author rkujawa
 *
 * Requires: Cargo, Cargo JBoss modules, Apache Discovery, JBoss Client Libraries
 */

String JBossId = "jboss71x"

ConfigurationFactory confFactory = new DefaultConfigurationFactory()
ContainerFactory contFactory = new DefaultContainerFactory()
DeployerFactory deployerFactory = new DefaultDeployerFactory()

Configuration conf = confFactory.createConfiguration(JBossId, ContainerType.REMOTE, ConfigurationType.RUNTIME)

conf.setProperty(GeneralPropertySet.HOSTNAME, "localhost")
conf.setProperty(JBossPropertySet.JBOSS_MANAGEMENT_PORT, "9999")
conf.setProperty(RemotePropertySet.USERNAME, "admin")
conf.setProperty(RemotePropertySet.PASSWORD, "secret")

RemoteContainer rc = contFactory.createContainer(JBossId, ContainerType.REMOTE, conf)
Deployer deployer = deployerFactory.createDeployer(rc, DeployerType.REMOTE)
Deployable testWAR;

testWAR = new JBossWAR("/tmp/foo.war")

deployer.deploy(testWAR)
