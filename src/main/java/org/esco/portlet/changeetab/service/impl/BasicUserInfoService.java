/**
 * 
 */
package org.esco.portlet.changeetab.service.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
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

	private static final Map<String, String> BASIC_USER_INFO = new HashMap<String, String>();
	static {
		BasicUserInfoService.BASIC_USER_INFO.put(BasicUserInfoService.ESCO_UAI_USER_ATTR_KEY, "1234567C,1234567D");
		BasicUserInfoService.BASIC_USER_INFO.put(BasicUserInfoService.ESCO_UAI_COURANT_USER_ATTR_KEY, "1234567D");
	}

	private static final Map<String, String> EMPTY_USER_INFO = new HashMap<String, String>();

	static {
		BasicUserInfoService.EMPTY_USER_INFO.put(BasicUserInfoService.ESCO_UAI_USER_ATTR_KEY, "1234567B");
		BasicUserInfoService.EMPTY_USER_INFO.put(BasicUserInfoService.ESCO_UAI_COURANT_USER_ATTR_KEY, "1234567B");
	}

	private static final Map<String, String> TEST_USER_INFO = BasicUserInfoService.BASIC_USER_INFO;

	@Override
	public Collection<String> getChangeableEtabIds(final PortletRequest request) {
		final Collection<String> escoUai = new HashSet<String>(4);

		final String uais = this.getUserInfo(request, BasicUserInfoService.ESCO_UAI_USER_ATTR_KEY);
		if (StringUtils.hasText(uais)) {
			final String[] splittedUais = uais.split(",");
			escoUai.addAll(Arrays.asList(splittedUais));
		} else {
			BasicUserInfoService.LOG.error("Unable to retrieve {} attribute in Portal UserInfo !", BasicUserInfoService.ESCO_UAI_USER_ATTR_KEY);
			throw new IllegalStateException("EscoUai cannot be retrieved from Portal UserInfo !");
		}

		return escoUai;
	}

	@Override
	public String getCurrentEtabId(final PortletRequest request) {
		String escoUaiCourant = null;

		final String uaiCourant = this.getUserInfo(request, BasicUserInfoService.ESCO_UAI_COURANT_USER_ATTR_KEY);
		if (StringUtils.hasText(uaiCourant)) {
			escoUaiCourant = uaiCourant;
		} else {
			BasicUserInfoService.LOG.error("Unable to retrieve {} attribute in Portal UserInfo !", BasicUserInfoService.ESCO_UAI_COURANT_USER_ATTR_KEY);
			throw new IllegalStateException("EscoUaiCourant cannot be retrieved from Portal UserInfo !");
		}

		return escoUaiCourant;
	}

	/**
	 * Retrieve the user info attribute from portlet context, or the Mocked user info
	 * if the system property testEnv = true.
	 * 
	 * @param request the portlet request
	 * @param atributeName the attribute to retrieve
	 * @return the user info attribute value
	 */
	@SuppressWarnings("unchecked")
	public String getUserInfo(final PortletRequest request, final String attributeName) {
		Map<String, String> userInfo = (Map<String, String>) request.getAttribute(PortletRequest.USER_INFO);
		if ((userInfo == null) && "true".equals(System.getProperty("testEnv"))) {
			userInfo = BasicUserInfoService.TEST_USER_INFO;
		}

		String attributeValue = null;

		if (userInfo != null) {
			attributeValue = userInfo.get(attributeName);
		} else {
			BasicUserInfoService.LOG.error("Unable to retrieve Portal UserInfo !");
			throw new IllegalStateException("Unable to retrieve Portal UserInfo !");
		}

		return attributeValue;
	}

}
