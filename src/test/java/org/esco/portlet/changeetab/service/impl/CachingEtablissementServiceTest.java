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
package org.esco.portlet.changeetab.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.esco.portlet.changeetab.dao.IEtablissementDao;
import org.esco.portlet.changeetab.model.Etablissement;
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
@ContextConfiguration(locations="classpath:cachingEtablissementServiceContext.xml")
public class CachingEtablissementServiceTest {

	private static final String UAI_1 = "UAI_1";
	private static final String UAI_2 = "UAI_2";
	private static final String UAI_3 = "UAI_3";
	private static final String UAI_4 = "UAI_4";

	private static final Etablissement ETAB_1 = new Etablissement("1", CachingEtablissementServiceTest.UAI_1, "name1", "desc1");
	private static final Etablissement ETAB_2 = new Etablissement("2", CachingEtablissementServiceTest.UAI_2, "name2", "desc2");
	private static final Etablissement ETAB_3 = new Etablissement("3", CachingEtablissementServiceTest.UAI_3, "name3", "desc3");
	private static final Etablissement ETAB_4 = new Etablissement("4", CachingEtablissementServiceTest.UAI_4, "name4", "desc4");

	/** All etabs returned by mocked DAo. */
	private static final Collection<Etablissement> allEtabsFromDao = new ArrayList<Etablissement>(8);
	static {
		CachingEtablissementServiceTest.allEtabsFromDao.add(CachingEtablissementServiceTest.ETAB_1);
		CachingEtablissementServiceTest.allEtabsFromDao.add(CachingEtablissementServiceTest.ETAB_2);
		CachingEtablissementServiceTest.allEtabsFromDao.add(CachingEtablissementServiceTest.ETAB_3);
		CachingEtablissementServiceTest.allEtabsFromDao.add(CachingEtablissementServiceTest.ETAB_4);
	}

	@Autowired
	private CachingEtablissementService service;

	@SuppressWarnings("unused")
	private IEtablissementDao mockedDao;

	/**
	 * Setter of mockedDao.
	 *
	 * @param mockedDao the mockedDao to set
	 */
	@Autowired
	public void setMockedDao(final IEtablissementDao mockedDao) {
		this.mockedDao = mockedDao;
		// Init DAO mock
		Mockito.when(mockedDao.findAllEtablissements()).then(new Answer<Collection<Etablissement>>() {

			@Override
			public Collection<Etablissement> answer(InvocationOnMock invocation) throws Throwable {
				return CachingEtablissementServiceTest.this.mockedFindAllEtablissements();
			}
		});
	}

	@Test
	public void testRetrieveOneExistingEtab() throws Exception {
		final Collection<String> uais = new ArrayList<String>();
		uais.add(CachingEtablissementServiceTest.UAI_2);

		final Map<String, Etablissement> etabs = this.service.retrieveEtablissementsByCodes(uais);

		Assert.assertNotNull("Should return an empty collection !", etabs);
		Assert.assertEquals("Should return only one etab !", 1, etabs.size());
		Assert.assertTrue("Bad etab returned !", etabs.containsValue(CachingEtablissementServiceTest.ETAB_2));
	}

	@Test
	public void testRetrieveSeveralExistingEtabs() throws Exception {
		final Collection<String> uais = new ArrayList<String>();
		uais.add(CachingEtablissementServiceTest.UAI_3);
		uais.add(CachingEtablissementServiceTest.UAI_1);

		final Map<String, Etablissement> etabs = this.service.retrieveEtablissementsByCodes(uais);

		Assert.assertNotNull("Should return an empty collection !", etabs);
		Assert.assertEquals("Should return only one etab !", 2, etabs.size());
		Assert.assertTrue("Bad etab in returned list !", etabs.containsValue(CachingEtablissementServiceTest.ETAB_1));
		Assert.assertTrue("Bad etab in returned list !", etabs.containsValue(CachingEtablissementServiceTest.ETAB_3));
	}

	@Test
	public void testRetrieveNotExistingEtab() throws Exception {
		final Collection<String> uais = new ArrayList<String>();
		uais.add("NotExistingUai");

		final Map<String, Etablissement> etabs = this.service.retrieveEtablissementsByCodes(uais);

		Assert.assertNotNull("Should return an empty collection !", etabs);
		Assert.assertEquals("Should return an empty collection !", 0, etabs.size());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testRetrieveEtablissementsByUaisWithNullParam() throws Exception {
		this.service.retrieveEtablissementsByCodes(null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testRetrieveEtablissementsByUaisWithEmptyParam() throws Exception {
		final List<String> s = Collections.emptyList();
		this.service.retrieveEtablissementsByCodes(s);
	}

	@Test
	public void loadTestRetrieveSeveralExistingEtabs() throws Exception {
		this.service.setCachingDuration(100);
		
		long startTime = System.currentTimeMillis();
		
		int nbIterations = 10000;
		LoadRunner<CachingEtablissementServiceTest, Void> runner = new LoadRunner<CachingEtablissementServiceTest, Void>(nbIterations, 50, this) {

			@Override
			protected Void loadTest(CachingEtablissementServiceTest unitTest) throws Exception {
				
				final Collection<String> uais = new ArrayList<String>();
				uais.add(CachingEtablissementServiceTest.UAI_3);
				uais.add(CachingEtablissementServiceTest.UAI_1);

				final Map<String, Etablissement> etabs = CachingEtablissementServiceTest.this.service.retrieveEtablissementsByCodes(uais);

				Assert.assertNotNull("Should return an empty collection !", etabs);
				Assert.assertEquals("Should return only one etab !", 2, etabs.size());
				Assert.assertTrue("Bad etab in returned list !", etabs.containsValue(CachingEtablissementServiceTest.ETAB_1));
				Assert.assertTrue("Bad etab in returned list !", etabs.containsValue(CachingEtablissementServiceTest.ETAB_3));

				return null;
			}
		};
		
		Assert.assertTrue("LoadRunner run failed !", runner.getFinishedTestWithoutErrorCount() == nbIterations);
		
		long endTime = System.currentTimeMillis();
		
		System.out.println("Test take " + (endTime - startTime) + " ms.");
	}

	/**
	 * @return
	 */
	private Collection<Etablissement> mockedFindAllEtablissements() {
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return CachingEtablissementServiceTest.allEtabsFromDao;
	}

}
