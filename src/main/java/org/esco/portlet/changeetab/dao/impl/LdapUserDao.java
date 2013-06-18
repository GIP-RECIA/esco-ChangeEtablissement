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

import javax.naming.Name;
import javax.naming.directory.Attribute;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;

import org.esco.portlet.changeetab.dao.IUserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author GIP RECIA 2013 - Maxime BOSSARD.
 *
 */
@Service
public class LdapUserDao implements IUserDao, InitializingBean {

	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(LdapUserDao.class);

	private String userIdTemplate = "%u";

	@Autowired
	private LdapTemplate ldapTemplate;

	/** User base Ldap dn. */
	private String userDn;

	/** Current etab Id LDAP key. */
	private String currentEtabIdLdapKey;

	@Override
	public void saveCurrentEtablissement(final String userId, final String etabId) {
		LdapUserDao.LOG.debug("Saving current etablissement ...");

		final Attribute replaceCurrentEtabAttr = new BasicAttribute(this.currentEtabIdLdapKey, etabId);
		final Name dn = new DistinguishedName(this.userDn.replace(this.userIdTemplate, userId));
		final ModificationItem[] mods = new ModificationItem[1];

		mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, replaceCurrentEtabAttr);
		this.ldapTemplate.modifyAttributes(dn, mods);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(this.ldapTemplate, "No LdapTemplate configured !");
		Assert.hasText(this.userDn, "No user dn configured !");
		Assert.hasText(this.userIdTemplate, "No user Id template configured !");
		Assert.hasText(this.currentEtabIdLdapKey, "No current etab Id Ldap key configured !");

		Assert.state(this.userDn.contains(this.userIdTemplate), "User dn doesn't contain the user Id template !");
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
	 * Getter of userDn.
	 *
	 * @return the userDn
	 */
	public String getUserDn() {
		return this.userDn;
	}

	/**
	 * Setter of userDn.
	 *
	 * @param userDn the userDn to set
	 */
	public void setUserDn(final String userDn) {
		this.userDn = userDn;
	}

	/**
	 * Getter of currentEtabIdLdapKey.
	 *
	 * @return the currentEtabIdLdapKey
	 */
	public String getCurrentEtabIdLdapKey() {
		return this.currentEtabIdLdapKey;
	}

	/**
	 * Setter of currentEtabIdLdapKey.
	 *
	 * @param currentEtabIdLdapKey the currentEtabIdLdapKey to set
	 */
	public void setCurrentEtabIdLdapKey(final String currentEtabIdLdapKey) {
		this.currentEtabIdLdapKey = currentEtabIdLdapKey;
	}

	/**
	 * Getter of userIdTemplate.
	 *
	 * @return the userIdTemplate
	 */
	public String getUserIdTemplate() {
		return this.userIdTemplate;
	}

	/**
	 * Setter of userIdTemplate.
	 *
	 * @param userIdTemplate the userIdTemplate to set
	 */
	public void setUserIdTemplate(final String userIdTemplate) {
		this.userIdTemplate = userIdTemplate;
	}

}
