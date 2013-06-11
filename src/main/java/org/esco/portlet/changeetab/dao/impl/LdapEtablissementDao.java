/**
 * 
 */
package org.esco.portlet.changeetab.dao.impl;

import java.util.Collection;
import java.util.List;

import org.esco.portlet.changeetab.dao.IEtablissementDao;
import org.esco.portlet.changeetab.model.Etablissement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author GIP RECIA 2013 - Maxime BOSSARD.
 *
 */
@Service
public class LdapEtablissementDao implements IEtablissementDao, InitializingBean {

	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(LdapEtablissementDao.class);

	@Autowired
	private LdapTemplate ldapTemplate;

	/** Etablissements Ldap base. */
	private String etablissementBase;

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Etablissement> findAllEtablissements() {
		LdapEtablissementDao.LOG.debug("Finding all etablissements ...");

		final List<Etablissement> allEtabs = this.ldapTemplate.search(this.etablissementBase, "*", new EtablissementAttributesMapper());

		LdapEtablissementDao.LOG.debug("{} etablissements found.", allEtabs.size());

		return allEtabs;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(this.ldapTemplate, "No LdapTemplate configured !");
		Assert.hasText(this.etablissementBase, "No etablissement Ldap base configured !");
	}

	/**
	 * Getter of ldapTemplate.
	 *
	 * @return the ldapTemplate
	 */
	public LdapTemplate getLdapTemplate() {
		return this.ldapTemplate;
	}

	/**
	 * Setter of ldapTemplate.
	 *
	 * @param ldapTemplate the ldapTemplate to set
	 */
	public void setLdapTemplate(final LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}

	/**
	 * Getter of etablissementBase.
	 *
	 * @return the etablissementBase
	 */
	public String getEtablissementBase() {
		return this.etablissementBase;
	}

	/**
	 * Setter of etablissementBase.
	 *
	 * @param etablissementBase the etablissementBase to set
	 */
	public void setEtablissementBase(final String etablissementBase) {
		this.etablissementBase = etablissementBase;
	}

}
