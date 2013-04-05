import net.c0ff33.jbossutils.JBossJdbcPoolNagiosPlugin

/**
 * Example Nagios plugin to monitor JDBC connection pool.
 * @author rkujawa
 */

cli = new CliBuilder()
cli.with {
    usage: 'Self'
    w longOpt:'warn', 'warning percents', args:1
    c longOpt:'crit', 'critical percents',args:1
    d longOpt:'datasource', 'name of the datasource',args:1
    h longOpt:'hostname','host where JBoss is running', args:1
    p longOpt:'port','management port', args:1
    l longOpt:'login','login to JBoss ManagementRealm', args:1
    s longOpt:'pass','password to JBoss ManagementRealm', args:1
}

if(args.length == 0) {
    cli.usage()
    System.exit(3)
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
String dsName = opt.d
int warnPercent = Integer.parseInt(opt.w)
int critPercent = Integer.parseInt(opt.c)

JBossJdbcPoolNagiosPlugin p = new JBossJdbcPoolNagiosPlugin()

p.connect(hostname, port, login, password)
p.setDsName(dsName)
p.setWarnPercent(warnPercent)
p.setCritPercent(critPercent)
rv = p.check()
p.disconnect()

System.exit(rv)
