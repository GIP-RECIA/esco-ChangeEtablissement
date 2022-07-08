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

import javax.naming.Name;
import javax.naming.directory.Attribute;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.esco.portlet.changeetab.dao.IUserDao;
import org.esco.portlet.changeetab.model.Structure;
import org.esco.portlet.changeetab.model.UniteAdministrativeImmatriculee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.util.Assert;

/**
 * @author GIP RECIA 2013 - Maxime BOSSARD.
 *
 */
//@Service
@Data
@NoArgsConstructor
public class LdapUserDao implements IUserDao, InitializingBean {

	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(LdapUserDao.class);

	@NonNull
	private String userIdTemplate = "%u";

	//@Autowired
	private LdapTemplate ldapTemplate;

	/** User base Ldap dn. */
	@NonNull
	private String userDn;

	/** Current struct Id LDAP key. */
	@NonNull
	private String currentStructIdLdapKey;
	/** Current struct Code LDAP key. */
	@NonNull
	private String currentEtabCodeLdapKey;

	@Override
	public void saveCurrentStructure(final String userId, final Structure struct) {
		LdapUserDao.LOG.debug("Saving current structure ...");

		final Attribute replaceCurrentStructAttr = new BasicAttribute(this.currentStructIdLdapKey, struct.getId());
		ModificationItem[] mods = null;
		if (struct instanceof UniteAdministrativeImmatriculee) {
			final Attribute replaceCurrentEtabAttr = new BasicAttribute(this.currentEtabCodeLdapKey,
					((UniteAdministrativeImmatriculee) struct).getCode());
			mods = new ModificationItem[2];
			mods[1] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, replaceCurrentEtabAttr);

		} else {
			mods = new ModificationItem[1];
		}
		final Name dn = new DistinguishedName(this.userDn.replace(this.userIdTemplate, userId));
		mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, replaceCurrentStructAttr);
		this.ldapTemplate.modifyAttributes(dn, mods);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(this.ldapTemplate, "No LdapTemplate configured !");
		Assert.hasText(this.userDn, "No user dn configured !");
		Assert.hasText(this.userIdTemplate, "No user Id template configured !");
		Assert.hasText(this.currentStructIdLdapKey, "No current struct Id Ldap key configured !");
		Assert.hasText(this.currentEtabCodeLdapKey, "No current etab Code Ldap key configured !");

		Assert.state(this.userDn.contains(this.userIdTemplate), "User dn doesn't contain the user Id template !");
	}

}
