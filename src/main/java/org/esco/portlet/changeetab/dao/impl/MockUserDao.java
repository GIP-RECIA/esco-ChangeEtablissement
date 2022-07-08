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
import lombok.extern.slf4j.Slf4j;
import org.esco.portlet.changeetab.dao.IUserDao;
import org.esco.portlet.changeetab.model.Structure;
import org.esco.portlet.changeetab.model.UniteAdministrativeImmatriculee;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.util.Assert;

/**
 * @author GIP RECIA 2013 - Maxime BOSSARD.
 *
 */
//@Service
@Data
@Slf4j
@NoArgsConstructor
public class MockUserDao implements IUserDao, InitializingBean {

	@NonNull
	private String userIdTemplate = "%u";

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
		log.debug("Saving current etablissement ...");

		final Attribute replaceCurrentStructAttr = new BasicAttribute(this.currentStructIdLdapKey, struct.getId());
		final Name dn = new DistinguishedName(this.userDn.replace(this.userIdTemplate, userId));
		ModificationItem[] mods = null;
		Attribute replaceCurrentEtabAttr = null;
		if (struct instanceof UniteAdministrativeImmatriculee) {
			replaceCurrentEtabAttr = new BasicAttribute(this.currentEtabCodeLdapKey,
					((UniteAdministrativeImmatriculee) struct).getCode());
			mods = new ModificationItem[2];
			mods[1] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, replaceCurrentEtabAttr);

		} else {
			mods = new ModificationItem[1];
		}

		mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, replaceCurrentStructAttr);

		log.info("Should call ldaptemplate.modifyAttributes() with userDn: [{}] and ldapAttr: [{}], [{}]", dn,
				replaceCurrentStructAttr, replaceCurrentEtabAttr);

	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.hasText(this.userDn, "No user dn configured !");
		Assert.hasText(this.userIdTemplate, "No user Id template configured !");
		Assert.hasText(this.currentStructIdLdapKey, "No current struct Id Ldap key configured !");
		Assert.hasText(this.currentEtabCodeLdapKey, "No current etab Code Ldap key configured !");

		Assert.state(this.userDn.contains(this.userIdTemplate), "User dn doesn't contain the user Id template !");
	}
}
