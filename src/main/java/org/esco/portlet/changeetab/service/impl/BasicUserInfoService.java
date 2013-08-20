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
package org.esco.portlet.changeetab.service.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;

import org.esco.portlet.changeetab.service.IUserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * @author GIP RECIA 2013 - Maxime BOSSARD.
 *
 */
public class BasicUserInfoService implements IUserInfoService, InitializingBean {

	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(BasicUserInfoService.class);

	private String etabCodesInfoKey;

	private String currentEtabCodeInfoKey;

	private final Map<String, List<String>> basicUserInfoMap = new HashMap<String, List<String>>();

	private final Map<String, List<String>> emptyUserInfoMap = new HashMap<String, List<String>>();
	
	@SuppressWarnings("unused")
	private final Map<String, List<String>> nullUserInfoMap = null;

	private final Map<String, List<String>> testUserInfoMap = this.basicUserInfoMap;

	@Override
	public Collection<String> getChangeableEtabCodes(final PortletRequest request) {
		final Collection<String> etabCodes = this.getUserInfo(request, this.etabCodesInfoKey);
		final Collection<String> etabCodesLowerCase = new HashSet<String>(etabCodes.size());

		if (etabCodes.isEmpty()) {
			// Multivalued attribute which should not be empty
			BasicUserInfoService.LOG.warn("Unable to retrieve {} attribute in Portal UserInfo !", this.etabCodesInfoKey);
		} else {
			for (final String id : etabCodes) {
				etabCodesLowerCase.add(id.toLowerCase());
			}
		}

		return etabCodesLowerCase;
	}

	@Override
	public String getCurrentEtabCode(final PortletRequest request) {
		String escoUaiCourant = null;

		final List<String> uaiCourant = this.getUserInfo(request, this.currentEtabCodeInfoKey);

		if (uaiCourant.size() == 1) {
			// Monovalued attribute
			escoUaiCourant = uaiCourant.iterator().next().toLowerCase();
		}

		if (!StringUtils.hasText(escoUaiCourant)) {
			escoUaiCourant = null;
			BasicUserInfoService.LOG.warn("Unable to retrieve {} attribute in Portal UserInfo !", this.currentEtabCodeInfoKey);
		}

		return escoUaiCourant;
	}

	@Override
	public String getUserId(final PortletRequest request) {
		return request.getRemoteUser();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.hasText(this.etabCodesInfoKey, "No Etab Ids user info key configured !");
		Assert.hasText(this.currentEtabCodeInfoKey, "No Current Etab Id user info key configured !");

		this.basicUserInfoMap.put(this.etabCodesInfoKey, Arrays.asList(new String[]{"0450822x","0333333y","0377777U"}));
		this.basicUserInfoMap.put(this.currentEtabCodeInfoKey, Arrays.asList(new String[]{"0450822X"}));

		this.emptyUserInfoMap.put(this.etabCodesInfoKey, Arrays.asList(new String[]{"1234567b"}));
		this.emptyUserInfoMap.put(this.currentEtabCodeInfoKey, Arrays.asList(new String[]{"1234567B"}));
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
			userInfo = this.testUserInfoMap;
		}

		List<String> attributeValues = null;

		if (userInfo != null) {
			attributeValues = userInfo.get(attributeName);
		} else {
			BasicUserInfoService.LOG.error("Unable to retrieve Portal UserInfo !");
			//throw new IllegalStateException("Unable to retrieve Portal UserInfo !");
		}

		if (attributeValues == null) {
			attributeValues = Collections.EMPTY_LIST;
		}

		return attributeValues;
	}

	/**
	 * Getter of etabIdsInfoKey.
	 *
	 * @return the etabIdsInfoKey
	 */
	public String getEtabIdsInfoKey() {
		return this.etabCodesInfoKey;
	}

	/**
	 * Setter of etabIdsInfoKey.
	 *
	 * @param etabIdsInfoKey the etabIdsInfoKey to set
	 */
	public void setEtabIdsInfoKey(final String etabIdsInfoKey) {
		this.etabCodesInfoKey = etabIdsInfoKey;
	}

	/**
	 * Getter of currentEtabIdInfoKey.
	 *
	 * @return the currentEtabIdInfoKey
	 */
	public String getCurrentEtabIdInfoKey() {
		return this.currentEtabCodeInfoKey;
	}

	/**
	 * Setter of currentEtabIdInfoKey.
	 *
	 * @param currentEtabIdInfoKey the currentEtabIdInfoKey to set
	 */
	public void setCurrentEtabIdInfoKey(final String currentEtabIdInfoKey) {
		this.currentEtabCodeInfoKey = currentEtabIdInfoKey;
	}

}
