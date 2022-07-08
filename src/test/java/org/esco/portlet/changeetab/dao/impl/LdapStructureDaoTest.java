/*
 * Copyright (C) 2017 GIP RECIA http://www.recia.fr
 * @Author (C) 2013 Maxime Bossard <mxbossard@gmail.com>
 * @Author (C) 2016 Julien Gribonvald <julien.gribonvald@recia.fr>
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

import java.util.Collection;

import lombok.extern.slf4j.Slf4j;

import org.esco.portlet.changeetab.dao.IStructureDao;
import org.esco.portlet.changeetab.model.Structure;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * FIXME: Unable to load the Apache Directory for the test !
 *
 * @author GIP RECIA 2013 - Maxime BOSSARD.
 *
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:ldapStructureDaoContext.xml")
public class LdapStructureDaoTest {

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
	private IStructureDao dao;

	/*@Value(value = "classpath:esco-structure-schema.ldif")
	private Resource escoStructuresSchemaLdif;

	@Value(value = "classpath:init.ldif")
	private Resource initLdif;*/

	@Rule
	public final LdapServerRule LDAP_RULE = new LdapServerRule(defaultPartitionSuffix, ClassLoader.getSystemResource(
			"init.ldif").getPath(), LdapStructureDaoTest.port, true, ClassLoader.getSystemResource(
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
	public void testFindAllStructures() throws Exception {
		final Collection<? extends Structure> structs = this.dao.findAllStructures();

		Assert.assertNotNull("Structs list shoud be empty not null !", structs);

		Assert.assertTrue("Structs list shoud not be empty !", structs.size() > 0);

		for (Structure struct : structs) {
			log.debug("returned struct : {}", struct);
		}
	}

	@Test
	public void testFindOneStructures() throws Exception {
		final Collection<? extends Structure> structs = this.dao.findAllStructures();

		Assert.assertNotNull("Structs list shoud be empty not null !", structs);

		Assert.assertTrue("Structs list shoud not be empty !", structs.size() > 0);

		final Structure structComparison = structs.iterator().next();

		final Structure structToCompare = this.dao.findOneStructureById(structComparison.getId());

		Assert.assertTrue("Struct comparison should be equal", structToCompare.equals(structComparison));
	}
}
