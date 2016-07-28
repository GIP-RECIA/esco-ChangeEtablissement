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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author GIP RECIA 2013 - Maxime BOSSARD.
 *
 */
@Service
public class MockEtablissementDao implements IEtablissementDao, InitializingBean {

	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(MockEtablissementDao.class);

	@Override
	public Collection<Etablissement> findAllEtablissements() {
		MockEtablissementDao.LOG.debug("Finding all etablissements ...");

		final Collection<Etablissement> allEtabs = new HashSet<Etablissement>(8);
		allEtabs.add(new Etablissement("1234567A", "1234567a", "Etab A", "Etab A", "Desc A"));
		allEtabs.add(new Etablissement("1234567B", "1234567b", "Etab B", "Etab B", "Desc B"));
		allEtabs.add(new Etablissement("1234567C", "1234567c", "Etab C", "Etab C", "Desc C"));
		allEtabs.add(new Etablissement("1234567D", "1234567d", "Etab D", "Etab D", "Desc D"));
		allEtabs.add(new Etablissement("1234567E", "1234567e", "Etab E", "Etab E", "Desc E"));

		MockEtablissementDao.LOG.debug("{} etablissements found.", allEtabs.size());

		return allEtabs;
	}

	@Override
	public void afterPropertiesSet() throws Exception {

	}

}
