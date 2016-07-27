/**
 * Copyright (C) 2012 RECIA http://www.recia.fr
 * @Author (C) 2012 Maxime Bossard <mxbossard@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 *
 */
package org.esco.portlet.changeetab.dao.impl;

import org.esco.portlet.changeetab.dao.IEtablissementDao;
import org.esco.portlet.changeetab.model.Etablissement;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;

/**
 * FIXME: Unable to load the Apache Directory for the test !
 *
 * @author GIP RECIA 2013 - Maxime BOSSARD.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:ldapEtablissementDaoContext.xml")
public class LdapEtablissementDaoTest {

	private static final Logger LOG = LoggerFactory.getLogger(LdapEtablissementDaoTest.class);

	private static int port = 42539;
	private static String defaultPartitionSuffix = "dc=esco-centre,dc=fr";
	private static String defaultPartitionName = "root";
	private static String principal = "uid=admin,ou=system";
	private static String credentials = "secret";

	/*@Autowired
	private LdapContextSource contextSource;*/

	@Autowired
	private LdapTemplate ldapTemplate;

	@Autowired
	private IEtablissementDao dao;

	/*@Value(value = "classpath:esco-structure-schema.ldif")
	private Resource escoStructuresSchemaLdif;

	@Value(value = "classpath:init.ldif")
	private Resource initLdif;*/

	@Rule
	public final LdapServerRule LDAP_RULE = new LdapServerRule(defaultPartitionSuffix, ClassLoader.getSystemResource(
			"init.ldif").getPath(), LdapEtablissementDaoTest.port, true, ClassLoader.getSystemResource(
			"esco-structure-schema.ldif").getPath());

	/*@Before
	public void initLdap() throws Exception {
	    final DistinguishedName schemaDn = new DistinguishedName("ou=schema");
	    final DistinguishedName structuresDn = new DistinguishedName("ou=structures,dc=esco-centre,dc=fr");

	    LdapTestUtils.cleanAndSetup(this.ldapTemplate.getContextSource(), schemaDn, this.escoStructuresSchemaLdif);
	    //LdapTestUtils.loadLdif(this.ldapTemplate.getContextSource(), this.initLdif);
	    //LdapTestUtils.cleanAndSetup(this.ldapTemplate.getContextSource(), structuresDn, this.initLdif);
	}*/

	@Test
	public void testFindAllEtablissements() throws Exception {
		final Collection<Etablissement> etabs = this.dao.findAllEtablissements();

		Assert.assertNotNull("Etabs list shoud be empty not null !", etabs);

		Assert.assertTrue("Etabs list shoud not be empty !", etabs.size() > 0);

		for (Etablissement etab : etabs) {
			LOG.debug("returned étab : {}", etab);
		}
	}

}
