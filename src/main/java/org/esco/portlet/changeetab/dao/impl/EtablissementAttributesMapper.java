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

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.esco.portlet.changeetab.model.Etablissement;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.util.Assert;

/**
 * @author GIP RECIA 2013 - Maxime BOSSARD.
 *
 */
public class EtablissementAttributesMapper implements AttributesMapper {

	private final String idAttrKey;

	private final String nameAttrKey;

	private final String descriptionAttrKey;

	/**
	 * @param idAttrKey
	 * @param nameAttrKey
	 * @param descriptionAttrKey
	 */
	public EtablissementAttributesMapper(final String idAttrKey, final String nameAttrKey,
			final String descriptionAttrKey) {
		super();

		Assert.hasText(idAttrKey, "Etab Id LDAP attr key not supplied !");
		Assert.hasText(nameAttrKey, "Etab Name LDAP attr key not supplied !");
		Assert.hasText(descriptionAttrKey, "Etab Description LDAP attr key not supplied !");

		this.idAttrKey = idAttrKey;
		this.nameAttrKey = nameAttrKey;
		this.descriptionAttrKey = descriptionAttrKey;
	}

	@Override
	public Object mapFromAttributes(final Attributes attrs) throws NamingException {
		final Etablissement etab = new Etablissement();

		etab.setId((String) attrs.get(this.idAttrKey).get());
		etab.setName((String) attrs.get(this.nameAttrKey).get());
		etab.setDescription((String) attrs.get(this.descriptionAttrKey).get());

		Assert.hasText(etab.getId(), "No UAI attribute found in LDAP for Etablissement !");
		Assert.hasText(etab.getName(), "No Name attribute found in LDAP for Etablissement !");
		Assert.hasText(etab.getDescription(), "No Description attribute found in LDAP for Etablissement !");

		return etab;
	}

}
