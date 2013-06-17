/**
 * 
 */
package org.esco.portlet.changeetab.service.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;

import org.esco.portlet.changeetab.service.IUserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * @author GIP RECIA 2013 - Maxime BOSSARD.
 *
 */
public class BasicUserInfoService implements IUserInfoService {

	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(BasicUserInfoService.class);

	private static final String ESCO_UAI_USER_ATTR_KEY = "ESCOUAI";

	private static final String ESCO_UAI_COURANT_USER_ATTR_KEY = "ESCOUAICourant";

	private static final Map<String, List<String>> BASIC_USER_INFO = new HashMap<String, List<String>>();
	static {
		BasicUserInfoService.BASIC_USER_INFO.put(BasicUserInfoService.ESCO_UAI_USER_ATTR_KEY,
				Arrays.asList(new String[]{"1234567C","1234567D","1234567A"}));
		BasicUserInfoService.BASIC_USER_INFO.put(BasicUserInfoService.ESCO_UAI_COURANT_USER_ATTR_KEY,
				Arrays.asList(new String[]{"1234567D"}));
	}

	private static final Map<String, List<String>> EMPTY_USER_INFO = new HashMap<String, List<String>>();

	static {
		BasicUserInfoService.EMPTY_USER_INFO.put(BasicUserInfoService.ESCO_UAI_USER_ATTR_KEY,
				Arrays.asList(new String[]{"1234567B"}));
		BasicUserInfoService.EMPTY_USER_INFO.put(BasicUserInfoService.ESCO_UAI_COURANT_USER_ATTR_KEY,
				Arrays.asList(new String[]{"1234567B"}));
	}

	private static final Map<String, List<String>> TEST_USER_INFO = BasicUserInfoService.BASIC_USER_INFO;

	@Override
	public Collection<String> getChangeableEtabIds(final PortletRequest request) {
		final Collection<String> escoUais = this.getUserInfo(request, BasicUserInfoService.ESCO_UAI_USER_ATTR_KEY);

		if (escoUais.isEmpty()) {
			// Multivalued attribute which should not be empty
			BasicUserInfoService.LOG.error("Unable to retrieve {} attribute in Portal UserInfo !", BasicUserInfoService.ESCO_UAI_USER_ATTR_KEY);
			//throw new IllegalStateException("EscoUai cannot be retrieved from Portal UserInfo !");
		}

		return escoUais;
	}

	@Override
	public String getCurrentEtabId(final PortletRequest request) {
		String escoUaiCourant = null;

		final List<String> uaiCourant = this.getUserInfo(request, BasicUserInfoService.ESCO_UAI_COURANT_USER_ATTR_KEY);

		if (uaiCourant.size() == 1) {
			// Monovalued attribute
			escoUaiCourant = uaiCourant.iterator().next();
		}

		if (!StringUtils.hasText(escoUaiCourant)) {
			escoUaiCourant = null;
			BasicUserInfoService.LOG.warn("Unable to retrieve {} attribute in Portal UserInfo !", BasicUserInfoService.ESCO_UAI_COURANT_USER_ATTR_KEY);
			//throw new RuntimeException("EscoUaiCourant cannot be retrieved from Portal UserInfo !");
		}

		return escoUaiCourant;
	}

	/**
	 * Retrieve the user info attribute from portlet context, or the Mocked user info
	 * if the system property testEnv = true.
	 * 
	 * @param request the portlet request
	 * @param atributeName the attribute to retrieve
	 * @return the user info attribute values
	 */
	@SuppressWarnings("unchecked")
	public List<String> getUserInfo(final PortletRequest request, final String attributeName) {
		Map<String, List<String>> userInfo =
				(Map<String, List<String>>) request.getAttribute("org.jasig.portlet.USER_INFO_MULTIVALUED");

		if ((userInfo == null) && "true".equals(System.getProperty("testEnv"))) {
			userInfo = BasicUserInfoService.TEST_USER_INFO;
		}

		List<String> attributeValues = null;

		if (userInfo != null) {
			attributeValues = userInfo.get(attributeName);
		} else {
			BasicUserInfoService.LOG.error("Unable to retrieve Portal UserInfo !");
			throw new IllegalStateException("Unable to retrieve Portal UserInfo !");
		}

		return attributeValues;
	}

}
