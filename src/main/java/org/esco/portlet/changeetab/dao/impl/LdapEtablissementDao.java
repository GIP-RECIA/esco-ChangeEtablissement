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
import org.esco.portlet.changeetab.dao.bean.IEtablissementFormatter;
import org.esco.portlet.changeetab.model.Etablissement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author GIP RECIA 2013 - Maxime BOSSARD.
 *
 */
@Service
public class LdapEtablissementDao implements IEtablissementDao, InitializingBean {

	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(LdapEtablissementDao.class);

	private String allEtabsFilter;

	private String etabIdLdapAttr;

	private String etabNameLdapAttr;

	private String etabDisplayNameLdapAttr;

	private String etabDescriptionLdapAttr;

	@Autowired
	private LdapTemplate ldapTemplate;

	@Autowired(required = false)
	private List<IEtablissementFormatter> etablissementFormatters;

	/** Etablissements Ldap base. */
	private String etablissementBase;

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Etablissement> findAllEtablissements() {
		LdapEtablissementDao.LOG.debug("Finding all etablissements ...");
		
		List<Etablissement> allEtabs;
		try {
			allEtabs = this.ldapTemplate.search(this.etablissementBase,
				this.allEtabsFilter, new EtablissementAttributesMapper(this.etabIdLdapAttr, this.etabNameLdapAttr, this.etabDisplayNameLdapAttr, this.etabDescriptionLdapAttr,
							this.etablissementFormatters));
		} catch (final Exception e) {
			// We catch all exceptions, cause we don't want our portlet to block the portal.
			LOG.error("Error while searching for establishments in LDAP !", e);
			allEtabs = Collections.emptyList();
		}
		
		LdapEtablissementDao.LOG.debug("{} etablissements found.", allEtabs.size());

		return allEtabs;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(this.ldapTemplate, "No LdapTemplate configured !");
		Assert.hasText(this.etablissementBase, "No etablissement Ldap base configured !");

		Assert.hasText(this.allEtabsFilter, "No 'all etabs' Ldap filter configured !");
		Assert.hasText(this.etabIdLdapAttr, "No etablissement Id Ldap attribute configured !");
		Assert.hasText(this.etabNameLdapAttr, "No etablissement Name Ldap attribute configured !");
		Assert.hasText(this.etabDisplayNameLdapAttr, "No etablissement DisplayName Ldap attribute configured !");
		Assert.hasText(this.etabDescriptionLdapAttr, "No etablissement Description Ldap attribute configured !");
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

	/**
	 * Getter of etablissementFormatters.
	 *
	 * @return the etablissementFormatters
	 */
	public List<IEtablissementFormatter> getEtablissementFormatters() {
		return etablissementFormatters;
	}

	/**
	 * Setter of etablissementFormatters.
	 *
	 * @param etablissementFormatters the etablissementFormatters to set
	 */
	public void setEtablissementFormatters(List<IEtablissementFormatter> etablissementFormatters) {
		this.etablissementFormatters = etablissementFormatters;
	}

	/**
	 * Getter of etabIdLdapAttr.
	 *
	 * @return the etabIdLdapAttr
	 */
	public String getEtabIdLdapAttr() {
		return this.etabIdLdapAttr;
	}

	/**
	 * Setter of etabIdLdapAttr.
	 *
	 * @param etabIdLdapAttr the etabIdLdapAttr to set
	 */
	public void setEtabIdLdapAttr(final String etabIdLdapAttr) {
		this.etabIdLdapAttr = etabIdLdapAttr;
	}

	/**
	 * Getter of etabNameLdapAttr.
	 *
	 * @return the etabNameLdapAttr
	 */
	public String getEtabNameLdapAttr() {
		return this.etabNameLdapAttr;
	}

	/**
	 * Setter of etabNameLdapAttr.
	 *
	 * @param etabNameLdapAttr the etabNameLdapAttr to set
	 */
	public void setEtabNameLdapAttr(final String etabNameLdapAttr) {
		this.etabNameLdapAttr = etabNameLdapAttr;
	}

	/**
	 * Getter of etabDisplayNameLdapAttr.
	 *
	 * @return the etabDisplayNameLdapAttr
	 */
	public String getEtabDisplayNameLdapAttr() {
		return etabDisplayNameLdapAttr;
	}

	/**
	 * Setter of etabDisplayNameLdapAttr.
	 *
	 * @param etabDisplayNameLdapAttr the etabDisplayNameLdapAttr to set
	 */
	public void setEtabDisplayNameLdapAttr(String etabDisplayNameLdapAttr) {
		this.etabDisplayNameLdapAttr = etabDisplayNameLdapAttr;
	}

	/**
	 * Getter of etabDescriptionLdapAttr.
	 *
	 * @return the etabDescriptionLdapAttr
	 */
	public String getEtabDescriptionLdapAttr() {
		return this.etabDescriptionLdapAttr;
	}

	/**
	 * Setter of etabDescriptionLdapAttr.
	 *
	 * @param etabDescriptionLdapAttr the etabDescriptionLdapAttr to set
	 */
	public void setEtabDescriptionLdapAttr(final String etabDescriptionLdapAttr) {
		this.etabDescriptionLdapAttr = etabDescriptionLdapAttr;
	}

	/**
	 * Getter of allEtabsFilter.
	 *
	 * @return the allEtabsFilter
	 */
	public String getAllEtabsFilter() {
		return this.allEtabsFilter;
	}

	/**
	 * Setter of allEtabsFilter.
	 *
	 * @param allEtabsFilter the allEtabsFilter to set
	 */
	public void setAllEtabsFilter(final String allEtabsFilter) {
		this.allEtabsFilter = allEtabsFilter;
	}


}
