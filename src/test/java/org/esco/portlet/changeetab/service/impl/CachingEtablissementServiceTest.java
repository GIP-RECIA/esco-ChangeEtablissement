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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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

	private static final Etablissement ETAB_1 = new Etablissement(CachingEtablissementServiceTest.UAI_1, "name1", "desc1");
	private static final Etablissement ETAB_2 = new Etablissement(CachingEtablissementServiceTest.UAI_2, "name2", "desc2");
	private static final Etablissement ETAB_3 = new Etablissement(CachingEtablissementServiceTest.UAI_3, "name3", "desc3");
	private static final Etablissement ETAB_4 = new Etablissement(CachingEtablissementServiceTest.UAI_4, "name4", "desc4");

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
		Mockito.when(mockedDao.findAllEtablissements()).thenReturn(CachingEtablissementServiceTest.allEtabsFromDao);
	}

	@Test
	public void testRetrieveOneExistingEtab() throws Exception {
		final Collection<String> uais = new ArrayList<String>();
		uais.add(CachingEtablissementServiceTest.UAI_2);

		final Map<String, Etablissement> etabs = this.service.retrieveEtablissementsByIds(uais);

		Assert.assertNotNull("Should return an empty collection !", etabs);
		Assert.assertEquals("Should return only one etab !", 1, etabs.size());
		Assert.assertTrue("Bad etab returned !", etabs.containsValue(CachingEtablissementServiceTest.ETAB_2));
	}

	@Test
	public void testRetrieveSeveralExistingEtabs() throws Exception {
		final Collection<String> uais = new ArrayList<String>();
		uais.add(CachingEtablissementServiceTest.UAI_3);
		uais.add(CachingEtablissementServiceTest.UAI_1);

		final Map<String, Etablissement> etabs = this.service.retrieveEtablissementsByIds(uais);

		Assert.assertNotNull("Should return an empty collection !", etabs);
		Assert.assertEquals("Should return only one etab !", 2, etabs.size());
		Assert.assertTrue("Bad etab in returned list !", etabs.containsValue(CachingEtablissementServiceTest.ETAB_1));
		Assert.assertTrue("Bad etab in returned list !", etabs.containsValue(CachingEtablissementServiceTest.ETAB_3));
	}

	@Test
	public void testRetrieveNotExistingEtab() throws Exception {
		final Collection<String> uais = new ArrayList<String>();
		uais.add("NotExistingUai");

		final Map<String, Etablissement> etabs = this.service.retrieveEtablissementsByIds(uais);

		Assert.assertNotNull("Should return an empty collection !", etabs);
		Assert.assertEquals("Should return an empty collection !", 0, etabs.size());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testRetrieveEtablissementsByUaisWithNullParam() throws Exception {
		this.service.retrieveEtablissementsByIds(null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testRetrieveEtablissementsByUaisWithEmptyParam() throws Exception {
		final List<String> s = Collections.emptyList();
		this.service.retrieveEtablissementsByIds(s);
	}

}
