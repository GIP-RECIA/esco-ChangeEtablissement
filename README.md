# esco-ChangeEtablissement

Portlet uPortal de changement d'établissement

## Maven commands
* To run test ldap dao class with apacheDS in debug
```
mvn clean test -Dtest=org.esco.portlet.changeetab.dao.impl.LdapEtablissementDaoTest -Dcom.unboundid.ldap.sdk.debug.enabled=true -Dcom.unboundid.ldap.sdk.debug.leve=INFO
```

* To run in dev mode use command : 
```
mvn clean portlet-prototyping:run
```
you can set user properties on pom.xml, in the maven portlet plugin configuration.

* To release the app
```
mvn clean package release:prepare -Dmaven.test.skip=true 
```
If you run test cases before use the argument `-Darguments="-DskipTests"` if you want to avoid to run test cases.
And finally :
```
mvn release:perform -Dmaven.test.skip=true
```