/**
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
package org.esco.portlet.changeetab.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import lombok.extern.slf4j.Slf4j;

import org.esco.portlet.changeetab.dao.IStructureDao;
import org.esco.portlet.changeetab.model.Structure;
import org.esco.portlet.changeetab.model.UniteAdministrativeImmatriculee;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fr.mby.utils.common.test.LoadRunner;

/**
 * @author GIP RECIA 2013 - Maxime BOSSARD.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:cachingStructureServiceContext.xml", "classpath:restApiContext.xml"})
@Slf4j
public class CachingStructureServiceTest {

	private static final List<String> valStreet_1 = new ArrayList<String>(1);
	private static final List<String> valStreet_2 = new ArrayList<String>(1);
	private static final List<String> valStreet_3 = new ArrayList<String>(1);
	private static final List<String> valStreet_4 = new ArrayList<String>(1);
	private static final List<String> valStreet_5 = new ArrayList<String>(1);
	private static final List<String> modified_valStreet_5 = new ArrayList<String>(1);
	static {
		CachingStructureServiceTest.valStreet_1.add("1 rue de l'éducation");
		CachingStructureServiceTest.valStreet_2.add("10 rue de l'éducation");
		CachingStructureServiceTest.valStreet_3.add("100 rue de l'éducation");
		CachingStructureServiceTest.valStreet_4.add("1000 rue de l'éducation");
		CachingStructureServiceTest.valStreet_5.add("2 rue de l'administration");
		CachingStructureServiceTest.modified_valStreet_5.add("modified 2 rue de l'administration");
	}
	private static final Map<String, List<String>> map_1 = new HashMap<String, List<String>>(1);
	private static final Map<String, List<String>> map_2 = new HashMap<String, List<String>>(1);
	private static final Map<String, List<String>> map_3 = new HashMap<String, List<String>>(1);
	private static final Map<String, List<String>> map_4 = new HashMap<String, List<String>>(1);
	private static final Map<String, List<String>> map_5 = new HashMap<String, List<String>>(1);
	private static final Map<String, List<String>> modified_map_5 = new HashMap<String, List<String>>(1);
	static {
		CachingStructureServiceTest.map_1.put("street", valStreet_1);
		CachingStructureServiceTest.map_2.put("street", valStreet_2);
		CachingStructureServiceTest.map_3.put("street", valStreet_3);
		CachingStructureServiceTest.map_4.put("street", valStreet_4);
		CachingStructureServiceTest.map_5.put("street", valStreet_5);
		CachingStructureServiceTest.modified_map_5.put("street", modified_valStreet_5);
	}

	private static final String SIREN_1 = "SIREN_1";
	private static final String SIREN_2 = "SIREN_2";
	private static final String SIREN_3 = "SIREN_3";
	private static final String SIREN_4 = "SIREN_4";
	private static final String SIREN_5 = "SIREN_5";

	private static final String UAI_1 = "UAI_1";
	private static final String UAI_2 = "UAI_2";
	private static final String UAI_3 = "UAI_3";
	private static final String UAI_4 = "UAI_4";

	private static final UniteAdministrativeImmatriculee ETAB_1 = new UniteAdministrativeImmatriculee(SIREN_1,
			CachingStructureServiceTest.UAI_1, "name1", "name1", "desc1", CachingStructureServiceTest.map_1);
	private static final UniteAdministrativeImmatriculee ETAB_2 = new UniteAdministrativeImmatriculee(SIREN_2,
			CachingStructureServiceTest.UAI_2, "name2", "name2", "desc2", CachingStructureServiceTest.map_2);
	private static final UniteAdministrativeImmatriculee ETAB_3 = new UniteAdministrativeImmatriculee(SIREN_3,
			CachingStructureServiceTest.UAI_3, "name3", "name3", "desc3", CachingStructureServiceTest.map_3);
	private static final UniteAdministrativeImmatriculee ETAB_4 = new UniteAdministrativeImmatriculee(SIREN_4,
			CachingStructureServiceTest.UAI_4, "name4", "name4", "desc4", CachingStructureServiceTest.map_4);
	private static final Structure STRUCT_5 = new Structure(SIREN_5, "name5", "name5", "desc5",
			CachingStructureServiceTest.map_5);
	private static final Structure MODIFIED_STRUCT_5 = new Structure(SIREN_5, "modified name5", "modified name5", "modified desc5",
			CachingStructureServiceTest.map_5);

	/** All etabs returned by mocked DAo. */
	private static final List<Structure> allStructsFromDao = new ArrayList(8);
	static {
		CachingStructureServiceTest.allStructsFromDao.add(CachingStructureServiceTest.ETAB_1);
		CachingStructureServiceTest.allStructsFromDao.add(CachingStructureServiceTest.ETAB_2);
		CachingStructureServiceTest.allStructsFromDao.add(CachingStructureServiceTest.ETAB_3);
		CachingStructureServiceTest.allStructsFromDao.add(CachingStructureServiceTest.ETAB_4);
		CachingStructureServiceTest.allStructsFromDao.add(CachingStructureServiceTest.STRUCT_5);
	}

	private static final List<Structure> allStructsWithModifiedFromDao = new ArrayList(5);
	static {
		CachingStructureServiceTest.allStructsWithModifiedFromDao.add(CachingStructureServiceTest.ETAB_1);
		CachingStructureServiceTest.allStructsWithModifiedFromDao.add(CachingStructureServiceTest.ETAB_2);
		CachingStructureServiceTest.allStructsWithModifiedFromDao.add(CachingStructureServiceTest.ETAB_3);
		CachingStructureServiceTest.allStructsWithModifiedFromDao.add(CachingStructureServiceTest.ETAB_4);
		CachingStructureServiceTest.allStructsWithModifiedFromDao.add(CachingStructureServiceTest.MODIFIED_STRUCT_5);
	}

	private static final Collection<UniteAdministrativeImmatriculee> emptyStructsFromDao = Collections.emptyList();

	@Autowired
	private CachingStructureService service;

	@SuppressWarnings("unused")
	private IStructureDao mockedDao;

	/**
	 * Setter of mockedDao.
	 *
	 * @param mockedDao the mockedDao to set
	 */
	@Autowired
	public void setMockedDao(final IStructureDao mockedDao) {
		this.mockedDao = mockedDao;
		// Init DAO mock
		Mockito.when(mockedDao.findAllStructures()).then(new Answer<Collection<? extends Structure>>() {

			@Override
			public Collection<? extends Structure> answer(InvocationOnMock invocation) throws Throwable {
				return CachingStructureServiceTest.this.mockedFindAllStructures();
			}
		});
	}

	@Test
	public void testRetrieveOneExistingEtab() throws Exception {
		final Collection<String> uais = new ArrayList<String>();
		uais.add(CachingStructureServiceTest.UAI_2);

		final Map<String, UniteAdministrativeImmatriculee> etabs = this.service.retrieveEtablissementsByCodes(uais);

		Assert.assertNotNull("Should return an empty collection !", etabs);
		Assert.assertEquals("Should return only one etab !", 1, etabs.size());
		Assert.assertTrue("Bad struct returned !", etabs.containsValue(CachingStructureServiceTest.ETAB_2));
	}

	@Test
	public void testRetrieveOneExistingStruct() throws Exception {
		final Collection<String> sirens = new ArrayList<String>();
		sirens.add(CachingStructureServiceTest.SIREN_2);

		final Map<String, Structure> structs = this.service.retrieveStructuresByIds(sirens);

		Assert.assertNotNull("Should return an empty collection !", structs);
		Assert.assertEquals("Should return only one etab !", 1, structs.size());
		Assert.assertTrue("Bad struct returned !", structs.containsValue(CachingStructureServiceTest.ETAB_2));
	}

	@Test
	public void testRetrieveSeveralExistingEtabs() throws Exception {
		final Collection<String> uais = new ArrayList<String>();
		uais.add(CachingStructureServiceTest.UAI_3);
		uais.add(CachingStructureServiceTest.UAI_1);

		final Map<String, UniteAdministrativeImmatriculee> etabs = this.service.retrieveEtablissementsByCodes(uais);

		Assert.assertNotNull("Should return an empty collection !", etabs);
		Assert.assertEquals("Should return only one etab !", 2, etabs.size());
		Assert.assertTrue("Bad etab in returned list !", etabs.containsValue(CachingStructureServiceTest.ETAB_1));
		Assert.assertTrue("Bad etab in returned list !", etabs.containsValue(CachingStructureServiceTest.ETAB_3));
	}

	@Test
	public void testRetrieveSeveralExistingStructs() throws Exception {
		final Collection<String> sirens = new ArrayList<String>();
		sirens.add(CachingStructureServiceTest.SIREN_3);
		sirens.add(CachingStructureServiceTest.SIREN_5);

		final Map<String, Structure> structs = this.service.retrieveStructuresByIds(sirens);

		Assert.assertNotNull("Should return an empty collection !", structs);
		Assert.assertEquals("Should return only one etab !", 2, structs.size());
		Assert.assertTrue("Bad struct in returned list !", structs.containsValue(CachingStructureServiceTest.ETAB_3));
		Assert.assertTrue("Bad struct in returned list !", structs.containsValue(CachingStructureServiceTest.STRUCT_5));
	}

	@Test
	public void testRetrieveNotExistingEtab() throws Exception {
		final Collection<String> uais = new ArrayList<String>();
		uais.add("NotExistingUai");

		final Map<String, UniteAdministrativeImmatriculee> etabs = this.service.retrieveEtablissementsByCodes(uais);

		Assert.assertNotNull("Should return an empty collection !", etabs);
		Assert.assertEquals("Should return an empty collection !", 0, etabs.size());
	}

	@Test
	public void testRetrieveNotExistingStruct() throws Exception {
		final Collection<String> sirens = new ArrayList<String>();
		sirens.add("NotExistingUai");

		final Map<String, Structure> structs = this.service.retrieveStructuresByIds(sirens);

		Assert.assertNotNull("Should return an empty collection !", structs);
		Assert.assertEquals("Should return an empty collection !", 0, structs.size());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRetrieveEtablissementsByUaisWithNullParam() throws Exception {
		this.service.retrieveEtablissementsByCodes(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRetrieveEtablissementsBySirensWithNullParam() throws Exception {
		this.service.retrieveStructuresByIds(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRetrieveEtablissementsByUaisWithEmptyParam() throws Exception {
		final List<String> s = Collections.emptyList();
		this.service.retrieveEtablissementsByCodes(s);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRetrieveEtablissementsBySirensWithEmptyParam() throws Exception {
		final List<String> s = Collections.emptyList();
		this.service.retrieveStructuresByIds(s);
	}

	@Test
	public void testRetrieveOneExistingEmptyEtab() throws Exception {
		final Collection<String> uais = new ArrayList<String>();
		uais.add(CachingStructureServiceTest.UAI_2);

		// Init DAO mock
		Mockito.when(mockedDao.findAllStructures()).then(new Answer<Collection<UniteAdministrativeImmatriculee>>() {

			@Override
			public Collection<UniteAdministrativeImmatriculee> answer(InvocationOnMock invocation) throws Throwable {
				return CachingStructureServiceTest.this.mockedFindEmptyStructures();
			}
		});

		final Map<String, UniteAdministrativeImmatriculee> etabs = this.service.retrieveEtablissementsByCodes(uais);

		Assert.assertNotNull("Should return an empty collection !", etabs);
	}

	@Test
	public void testRetrieveOneExistingEmptyStruct() throws Exception {
		final Collection<String> sirens = new ArrayList<String>();
		sirens.add(CachingStructureServiceTest.SIREN_2);

		// Init DAO mock
		Mockito.when(mockedDao.findAllStructures()).then(new Answer<Collection<? extends Structure>>() {

			@Override
			public Collection<? extends Structure> answer(InvocationOnMock invocation) throws Throwable {
				return CachingStructureServiceTest.this.mockedFindEmptyStructures();
			}
		});

		final Map<String, Structure> structs = this.service.retrieveStructuresByIds(sirens);

		Assert.assertNotNull("Should return an empty collection !", structs);
	}

	@Test
	public void testRetrieveSeveralExistingEmptyEtabs() throws Exception {
		final Collection<String> uais = new ArrayList<String>();
		uais.add(CachingStructureServiceTest.UAI_3);
		uais.add(CachingStructureServiceTest.UAI_1);

		// Init DAO mock
		Mockito.when(mockedDao.findAllStructures()).then(new Answer<Collection<UniteAdministrativeImmatriculee>>() {

			@Override
			public Collection<UniteAdministrativeImmatriculee> answer(InvocationOnMock invocation) throws Throwable {
				return CachingStructureServiceTest.this.mockedFindEmptyStructures();
			}
		});

		final Map<String, UniteAdministrativeImmatriculee> etabs = this.service.retrieveEtablissementsByCodes(uais);

		Assert.assertNotNull("Should return an empty collection !", etabs);
	}

	@Test
	public void testRetrieveSeveralExistingEmptyStructs() throws Exception {
		final Collection<String> sirens = new ArrayList<String>();
		sirens.add(CachingStructureServiceTest.SIREN_3);
		sirens.add(CachingStructureServiceTest.SIREN_5);

		// Init DAO mock
		Mockito.when(mockedDao.findAllStructures()).then(new Answer<Collection<? extends Structure>>() {

			@Override
			public Collection<? extends Structure> answer(InvocationOnMock invocation) throws Throwable {
				return CachingStructureServiceTest.this.mockedFindEmptyStructures();
			}
		});

		final Map<String, Structure> structs = this.service.retrieveStructuresByIds(sirens);

		Assert.assertNotNull("Should return an empty collection !", structs);
	}

	@Test
	public void testRetrieveNotExistingEmptyEtab() throws Exception {
		final Collection<String> uais = new ArrayList<String>();
		uais.add("NotExistingUai");

		final Map<String, UniteAdministrativeImmatriculee> etabs = this.service.retrieveEtablissementsByCodes(uais);

		// Init DAO mock
		Mockito.when(mockedDao.findAllStructures()).then(new Answer<Collection<UniteAdministrativeImmatriculee>>() {

			@Override
			public Collection<UniteAdministrativeImmatriculee> answer(InvocationOnMock invocation) throws Throwable {
				return CachingStructureServiceTest.this.mockedFindEmptyStructures();
			}
		});

		Assert.assertNotNull("Should return an empty collection !", etabs);
		Assert.assertEquals("Should return an empty collection !", 0, etabs.size());
	}

	@Test
	public void testRetrieveNotExistingEmptyStruct() throws Exception {
		final Collection<String> sirens = new ArrayList<String>();
		sirens.add("NotExistingUai");

		final Map<String, Structure> structs = this.service.retrieveStructuresByIds(sirens);

		// Init DAO mock
		Mockito.when(mockedDao.findAllStructures()).then(new Answer<Collection<? extends Structure>>() {

			@Override
			public Collection<? extends Structure> answer(InvocationOnMock invocation) throws Throwable {
				return CachingStructureServiceTest.this.mockedFindEmptyStructures();
			}
		});

		Assert.assertNotNull("Should return an empty collection !", structs);
		Assert.assertEquals("Should return an empty collection !", 0, structs.size());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRetrieveEmptyEtablissementsByUaisWithNullParam() throws Exception {
		// Init DAO mock
		Mockito.when(mockedDao.findAllStructures()).then(new Answer<Collection<UniteAdministrativeImmatriculee>>() {

			@Override
			public Collection<UniteAdministrativeImmatriculee> answer(InvocationOnMock invocation) throws Throwable {
				return CachingStructureServiceTest.this.mockedFindEmptyStructures();
			}
		});

		this.service.retrieveEtablissementsByCodes(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRetrieveEmptyStructuresBySirensWithNullParam() throws Exception {
		// Init DAO mock
		Mockito.when(mockedDao.findAllStructures()).then(new Answer<Collection<? extends Structure>>() {

			@Override
			public Collection<? extends Structure> answer(InvocationOnMock invocation) throws Throwable {
				return CachingStructureServiceTest.this.mockedFindEmptyStructures();
			}
		});

		this.service.retrieveStructuresByIds(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRetrieveEmptyEtablissementsByUaisWithEmptyParam() throws Exception {
		// Init DAO mock
		Mockito.when(mockedDao.findAllStructures()).then(new Answer<Collection<UniteAdministrativeImmatriculee>>() {

			@Override
			public Collection<UniteAdministrativeImmatriculee> answer(InvocationOnMock invocation) throws Throwable {
				return CachingStructureServiceTest.this.mockedFindEmptyStructures();
			}
		});

		final List<String> s = Collections.emptyList();
		this.service.retrieveEtablissementsByCodes(s);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRetrieveEmptyStructuresBySirensWithEmptyParam() throws Exception {
		// Init DAO mock
		Mockito.when(mockedDao.findAllStructures()).then(new Answer<Collection<? extends Structure>>() {

			@Override
			public Collection<? extends Structure> answer(InvocationOnMock invocation) throws Throwable {
				return CachingStructureServiceTest.this.mockedFindEmptyStructures();
			}
		});

		final List<String> s = Collections.emptyList();
		this.service.retrieveStructuresByIds(s);
	}

	@Test
	public void loadTestRetrieveSeveralExistingEtabs() throws Exception {
		this.service.setCachingDuration(100);

		// define a random on etabs retrived to test when the ldap dao returned errors
		Mockito.when(mockedDao.findAllStructures()).then(new Answer<Collection<? extends Structure>>() {

			@Override
			public Collection<? extends Structure> answer(InvocationOnMock invocation) throws Throwable {
				return CachingStructureServiceTest.this.randomMockedFindAllStructures();
			}
		});

		long startTime = System.currentTimeMillis();

		int nbIterations = 100000;
		LoadRunner<CachingStructureServiceTest, Void> runner = new LoadRunner<CachingStructureServiceTest, Void>(
				nbIterations, 100, this) {

			@Override
			protected Void loadTest(CachingStructureServiceTest unitTest) throws Exception {

				final Collection<String> uais = new ArrayList<String>();
				uais.add(CachingStructureServiceTest.UAI_3);
				uais.add(CachingStructureServiceTest.UAI_1);

				final Map<String, UniteAdministrativeImmatriculee> etabs = CachingStructureServiceTest.this.service
						.retrieveEtablissementsByCodes(uais);

				Assert.assertNotNull("Should return an empty collection !", etabs);
				//Manage the case of the randomMockedFindAllEtablissements returned 0 etabs
				if (etabs.size() != 0) {
					Assert.assertEquals("Should return only one etab !", 2, etabs.size());
					Assert.assertTrue("Bad etab in returned list !",
							etabs.containsValue(CachingStructureServiceTest.ETAB_1));
					Assert.assertTrue("Bad etab in returned list !",
							etabs.containsValue(CachingStructureServiceTest.ETAB_3));
				}

				return null;
			}
		};

		Assert.assertTrue("LoadRunner run failed !", runner.getFinishedTestWithoutErrorCount() == nbIterations);

		long endTime = System.currentTimeMillis();

		log.info("Test take {} ms.", (endTime - startTime));
	}

	@Test
	public void loadTestRetrieveSeveralExistingStructs() throws Exception {
		this.service.setCachingDuration(100);

		// define a random on etabs retrived to test when the ldap dao returned errors
		Mockito.when(mockedDao.findAllStructures()).then(new Answer<Collection<? extends Structure>>() {

			@Override
			public Collection<? extends Structure> answer(InvocationOnMock invocation) throws Throwable {
				return CachingStructureServiceTest.this.randomMockedFindAllStructures();
			}
		});

		long startTime = System.currentTimeMillis();

		int nbIterations = 100000;
		LoadRunner<CachingStructureServiceTest, Void> runner = new LoadRunner<CachingStructureServiceTest, Void>(
				nbIterations, 100, this) {

			@Override
			protected Void loadTest(CachingStructureServiceTest unitTest) throws Exception {

				final Collection<String> sirens = new ArrayList<String>();
				sirens.add(CachingStructureServiceTest.SIREN_5);
				sirens.add(CachingStructureServiceTest.SIREN_1);

				Random rd = new Random();

				if (rd.nextBoolean()) {
					CachingStructureServiceTest.this.service.invalidateStructureById(CachingStructureServiceTest.SIREN_5);
				}
				if (rd.nextBoolean()) {
					CachingStructureServiceTest.this.service.invalidateStructureById(CachingStructureServiceTest.SIREN_1);
				}

				final Map<String, Structure> structs = CachingStructureServiceTest.this.service
						.retrieveStructuresByIds(sirens);

				Assert.assertNotNull("Should return an empty collection !", structs);
				//Manage the case of the randomMockedFindAllEtablissements returned 0 etabs
				if (structs.size() != 0) {
					Assert.assertEquals("Should return only one struct !", 2, structs.size());
					Assert.assertTrue("Bad struct in returned list !",
							structs.containsValue(CachingStructureServiceTest.ETAB_1));
					Assert.assertTrue("Bad struct in returned list !",
							structs.containsValue(CachingStructureServiceTest.STRUCT_5));
				}

				return null;
			}
		};

		Assert.assertTrue("LoadRunner run failed !", runner.getFinishedTestWithoutErrorCount() == nbIterations);

		long endTime = System.currentTimeMillis();

		log.info("Test take {} ms.", (endTime - startTime));
	}

	// On refreshed struct before global refresh but not before RefreshExpiredDuration
	@Test
	public void loadTestInvalidatingStruct() throws Exception {
		this.service.setCachingDuration(700);
		this.service.setRefreshExpiredDuration(400);

		Mockito.when(mockedDao.findOneStructureById(Mockito.anyString())).then(new Answer<Structure>() {
			@Override
			public Structure answer(InvocationOnMock invocation) throws Throwable {
				return CachingStructureServiceTest.MODIFIED_STRUCT_5;
			}
		});

		long startTime = System.currentTimeMillis();

		// for the first call structures are retrieved from allStruct
		Structure struct = CachingStructureServiceTest.this.service.retrieveStructureById(CachingStructureServiceTest.SIREN_5);
		Assert.assertNotNull("Structure retrieved should not be null", struct);
		Assert.assertEquals("Bad struct in returned list !", CachingStructureServiceTest.STRUCT_5, struct);

		Mockito.when(mockedDao.findAllStructures()).then(new Answer<Collection<? extends Structure>>() {
			@Override
			public Collection<? extends Structure> answer(InvocationOnMock invocation) throws Throwable {
				return CachingStructureServiceTest.allStructsWithModifiedFromDao;
			}
		});

		CachingStructureServiceTest.this.service.invalidateStructureById(CachingStructureServiceTest.SIREN_5);
		long invalidated = System.currentTimeMillis();
		struct = CachingStructureServiceTest.this.service.retrieveStructureById(CachingStructureServiceTest.SIREN_5);

		// on check si on n'est pas en dehors de la durée de refresh et qu'on récupère toujours l'ancien non mis à jour
		if (!((invalidated + this.service.getRefreshExpiredDuration()) > System.currentTimeMillis())) {
			Assert.assertEquals("Bad struct in returned list !", CachingStructureServiceTest.STRUCT_5, struct);
		}
		while (!((invalidated + this.service.getRefreshExpiredDuration()) < System.currentTimeMillis())) {
			// wait
		}
		// should be refreshed for one struct and not globally
		struct = CachingStructureServiceTest.this.service.retrieveStructureById(CachingStructureServiceTest.SIREN_5);
		Assert.assertNotNull("Structure modified retrieved should not be null", struct);
		Assert.assertEquals("Bad struct in returned list !", CachingStructureServiceTest.MODIFIED_STRUCT_5, struct);

		long endTime = System.currentTimeMillis();

		log.info("Test take {} ms.", (endTime - startTime));
	}

	// on global refresh only
	@Test
	public void loadTestInvalidatingStruct2() throws Exception {
		this.service.setCachingDuration(500);
		this.service.setRefreshExpiredDuration(200);

		Mockito.when(mockedDao.findAllStructures()).then(new Answer<Collection<? extends Structure>>() {
			@Override
			public Collection<? extends Structure> answer(InvocationOnMock invocation) throws Throwable {
				return CachingStructureServiceTest.allStructsFromDao;
			}
		});

		long startTime = System.currentTimeMillis();

		Structure struct = CachingStructureServiceTest.this.service.retrieveStructureById(CachingStructureServiceTest.SIREN_5);
		long loaded = System.currentTimeMillis();

		Assert.assertNotNull("Structure retrieved should not be null", struct);
		Assert.assertEquals("Bad struct in returned list !", CachingStructureServiceTest.STRUCT_5, struct);

		Mockito.when(mockedDao.findAllStructures()).then(new Answer<Collection<? extends Structure>>() {
			@Override
			public Collection<? extends Structure> answer(InvocationOnMock invocation) throws Throwable {
				return CachingStructureServiceTest.allStructsWithModifiedFromDao;
			}
		});

		CachingStructureServiceTest.this.service.invalidateStructureById(CachingStructureServiceTest.SIREN_5);

		while (!((loaded + this.service.getCachingDuration()) < System.currentTimeMillis())) {
			// wait
		}

		struct = CachingStructureServiceTest.this.service.retrieveStructureById(CachingStructureServiceTest.SIREN_5);
		Assert.assertNotNull("Structure modified retrieved should not be null", struct);
		Assert.assertEquals("Bad struct modified  in returned list !", CachingStructureServiceTest.MODIFIED_STRUCT_5, struct);

		Assert.assertTrue("The Invalidated List should be empty !", CachingStructureServiceTest.this.service.getExpiredIds().isEmpty());

		long endTime = System.currentTimeMillis();

		log.info("Test take {} ms.", (endTime - startTime));
	}

	/**
	 * @return
	 */
	private Collection<? extends Structure> mockedFindAllStructures() {
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return CachingStructureServiceTest.allStructsFromDao;
	}

	/**
	 * @return
	 */
	private Collection<UniteAdministrativeImmatriculee> mockedFindEmptyStructures() {
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return CachingStructureServiceTest.emptyStructsFromDao;
	}

	/**
	 * @return
	 */
	private Collection<? extends Structure> randomMockedFindAllStructures() {
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Random random = new Random();
		int n = random.nextInt(10);
		if (n > 7) {
			log.debug("MockDao returns all Etabs");
			return CachingStructureServiceTest.allStructsFromDao;
		}
		log.debug("MockDao returns empty Etabs");
		return CachingStructureServiceTest.emptyStructsFromDao;
	}

}
