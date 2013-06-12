/**
 * 
 */
package org.esco.portlet.changeetab.dao.impl;

import java.util.Collection;
import java.util.HashSet;

import org.esco.portlet.changeetab.dao.IEtablissementDao;
import org.esco.portlet.changeetab.model.Etablissement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

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
		allEtabs.add(new Etablissement("1234567A", "Etab A", "Desc A"));
		allEtabs.add(new Etablissement("1234567B", "Etab B", "Desc B"));
		allEtabs.add(new Etablissement("1234567C", "Etab C", "Desc C"));
		allEtabs.add(new Etablissement("1234567D", "Etab D", "Desc D"));
		allEtabs.add(new Etablissement("1234567E", "Etab E", "Desc E"));

		MockEtablissementDao.LOG.debug("{} etablissements found.", allEtabs.size());

		return allEtabs;
	}

	@Override
	public void afterPropertiesSet() throws Exception {

	}

}
