import net.c0ff33.jbossutils.JBossAbstractNagiosPlugin
import net.c0ff33.jbossutils.JBossMemoryNagiosPlugin

/**
 * @author rkujawa
 */
cli = new CliBuilder()
cli.with {
    usage: 'Self'
    w longOpt:'warn', 'warning MB used', args:1
    c longOpt:'crit', 'critical MB used',args:1
    h longOpt:'hostname','host where JBoss is running', args:1
    p longOpt:'port','management port', args:1
    l longOpt:'login','login to JBoss ManagementRealm', args:1
    s longOpt:'pass','password to JBoss ManagementRealm', args:1
}

if(args.length == 0) {
    cli.usage()
    System.exit(JBossAbstractNagiosPlugin.NAGIOS_RETURN_UNKNOWN)
}

opt = cli.parse(args)

String hostname = opt.h
int port

if (opt.p)
    port = opt.p
else
    port = 9999

String login = opt.l
String password = opt.s
int warnMb = Integer.parseInt(opt.w)
int critMb = Integer.parseInt(opt.c)

JBossMemoryNagiosPlugin p = new JBossMemoryNagiosPlugin()

p.connect(hostname, port, login, password)
p.setUsedMbWarn(warnMb)
p.setUsedMbCrit(critMb)
rv = p.check()
p.disconnect()

System.exit(rv)
