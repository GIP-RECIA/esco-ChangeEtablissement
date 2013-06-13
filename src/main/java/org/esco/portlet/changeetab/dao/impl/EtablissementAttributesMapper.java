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

	private static final String UAI_ATTR_KEY = "ENTStructureUAI";
	private static final String NAME_ATTR_KEY = "ENTStructureNomCourant";
	private static final String DESCRIPTION_ATTR_KEY = "ENTStructureTypeStruct";

	@Override
	public Object mapFromAttributes(final Attributes attrs) throws NamingException {
		final Etablissement etab = new Etablissement();

		etab.setId((String) attrs.get(EtablissementAttributesMapper.UAI_ATTR_KEY).get());
		etab.setName((String) attrs.get(EtablissementAttributesMapper.NAME_ATTR_KEY).get());
		etab.setDescription((String) attrs.get(EtablissementAttributesMapper.DESCRIPTION_ATTR_KEY).get());

		Assert.hasText(etab.getId(), "No UAI attribute found in LDAP for Etablissement !");
		Assert.hasText(etab.getName(), "No Name attribute found in LDAP for Etablissement !");
		Assert.hasText(etab.getDescription(), "No Description attribute found in LDAP for Etablissement !");

		return etab;
	}

}
