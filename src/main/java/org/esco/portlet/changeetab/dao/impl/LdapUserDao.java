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

	private final String userIdTemplate = "%u";

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

}
