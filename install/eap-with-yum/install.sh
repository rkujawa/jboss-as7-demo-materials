#!/bin/sh
yum -y groupinstall jboss-eap6
yum -y install mod_jk-ap22 mod_cluster-native
