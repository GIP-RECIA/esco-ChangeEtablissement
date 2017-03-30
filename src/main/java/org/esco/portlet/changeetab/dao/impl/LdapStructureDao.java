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

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import org.esco.portlet.changeetab.dao.IStructureDao;
import org.esco.portlet.changeetab.dao.bean.IStructureFormatter;
import org.esco.portlet.changeetab.model.Structure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;

/**
 * @author GIP RECIA 2013 - Maxime BOSSARD.
 *
 */
@Service
@Data
@Slf4j
@NoArgsConstructor
public class LdapStructureDao implements IStructureDao/*, InitializingBean*/{

	@NonNull
	private String allStructFilter;
	@NonNull
	private String structIdLdapAttr;
	@NonNull
	private String etabcodeLdapAttr;
	@NonNull
	private String structNameLdapAttr;
	@NonNull
	private String structDisplayNameLdapAttr;
	@NonNull
	private String structDescriptionLdapAttr;

	private Set<String> otherAttributes;
	@NonNull
	private Set<String> classValueStructUAI;

	@Autowired
	private LdapTemplate ldapTemplate;

	@Autowired(required = false)
	private List<IStructureFormatter> structureFormatters;

	/** Structure Ldap base. */
	@NonNull
	private String structureBase;

	@Override
	@SuppressWarnings("unchecked")
	public Collection<? extends Structure> findAllStructures() {
		log.debug("Finding all structures ...");

		List<Structure> allStructs;
		try {
			allStructs = this.ldapTemplate.search(this.structureBase, this.allStructFilter,
					new StructureAttributesMapper(this.structIdLdapAttr, this.etabcodeLdapAttr,
							this.structNameLdapAttr, this.structDisplayNameLdapAttr, this.structDescriptionLdapAttr,
							this.otherAttributes, this.classValueStructUAI, this.structureFormatters));
		} catch (final Exception e) {
			// We catch all exceptions, cause we don't want our portlet to block the portal.
			log.error("Error while searching for structures in LDAP !", e);
			allStructs = Collections.emptyList();
		}

		log.debug("{} structures found.", allStructs.size());

		return allStructs;
	}

}
