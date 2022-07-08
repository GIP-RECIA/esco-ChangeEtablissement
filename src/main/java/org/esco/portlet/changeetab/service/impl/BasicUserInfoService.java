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
package org.esco.portlet.changeetab.service.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import org.esco.portlet.changeetab.service.IUserInfoService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * @author GIP RECIA 2013 - Maxime BOSSARD.
 *
 */
@Data
@NoArgsConstructor
@Slf4j
public class BasicUserInfoService implements IUserInfoService, InitializingBean {

	@NonNull
	private String etabCodesInfoKey;
	@NonNull
	private String currentEtabCodeInfoKey;
	@NonNull
	private String structIdsInfoKey;
	@NonNull
	private String currentStructIdInfoKey;

	private final Map<String, List<String>> basicUserInfoMap = new HashMap<String, List<String>>();

	private final Map<String, List<String>> emptyUserInfoMap = new HashMap<String, List<String>>();

	@SuppressWarnings("unused")
	private final Map<String, List<String>> nullUserInfoMap = null;

	private final Map<String, List<String>> testUserInfoMap = this.basicUserInfoMap;

	@Override
	public Collection<String> getChangeableEtabCodes(final PortletRequest request) {
		final Collection<String> etabCodes = this.getUserInfo(request, this.etabCodesInfoKey);
		final Collection<String> etabCodesCaseSensitive = new HashSet<String>(etabCodes.size());

		if (etabCodes.isEmpty()) {
			// Multivalued attribute which should not be empty
			log.warn("Unable to retrieve {} attribute in Portal UserInfo !", this.etabCodesInfoKey);
		} else {
			for (final String id : etabCodes) {
				etabCodesCaseSensitive.add(id.toUpperCase());
			}
		}

		return etabCodesCaseSensitive;
	}

	@Override
	public Collection<String> getChangeableStructIds(final PortletRequest request) {
		final Collection<String> structIds = this.getUserInfo(request, this.structIdsInfoKey);

		if (structIds.isEmpty()) {
			// Multivalued attribute which should not be empty
			log.warn("Unable to retrieve {} attribute in Portal UserInfo !", this.structIdsInfoKey);
		}
		return structIds;
	}

	@Override
	public String getCurrentEtabCode(final PortletRequest request) {
		String escoUaiCourant = null;

		final List<String> uaiCourant = this.getUserInfo(request, this.currentEtabCodeInfoKey);

		if (uaiCourant.size() == 1) {
			// Monovalued attribute
			escoUaiCourant = uaiCourant.iterator().next().toUpperCase();
		}

		if (!StringUtils.hasText(escoUaiCourant)) {
			escoUaiCourant = null;
			log.warn("Unable to retrieve {} attribute in Portal UserInfo !", this.currentEtabCodeInfoKey);
		}

		return escoUaiCourant;
	}

	@Override
	public String getCurrentStructId(final PortletRequest request) {
		String escoSIRENCourant = null;

		final List<String> sirenCourant = this.getUserInfo(request, this.currentStructIdInfoKey);

		if (sirenCourant.size() == 1) {
			// Monovalued attribute
			escoSIRENCourant = sirenCourant.iterator().next();
		}

		if (!StringUtils.hasText(escoSIRENCourant)) {
			escoSIRENCourant = null;
			log.warn("Unable to retrieve {} attribute in Portal UserInfo !", this.currentStructIdInfoKey);
		}

		return escoSIRENCourant;
	}

	@Override
	public String getUserId(final PortletRequest request) {
		return request.getRemoteUser();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.hasText(this.etabCodesInfoKey, "No Etab Codes user info key configured !");
		Assert.hasText(this.currentEtabCodeInfoKey, "No Current Etab Code user info key configured !");
		Assert.hasText(this.structIdsInfoKey, "No Struct Ids user info key configured !");
		Assert.hasText(this.currentStructIdInfoKey, "No Current Struct Id user info key configured !");

		final String[] etabs = System.getProperty("etablissement-swapper.userEtabs", "0450822x,0333333y,0377777U")
				.split(",");
		final String[] current = System.getProperty("etablissement-swapper.userCurrentEtab", "0450822X").split(",");
		final String[] structs = System.getProperty("etablissement-swapper.userStructs",
				"88888888888888,37373737373737,00000000000001").split(",");
		final String[] currentStruct = System.getProperty("etablissement-swapper.userCurrentStruct", "88888888888888")
				.split(",");
		this.basicUserInfoMap.put(this.etabCodesInfoKey, Arrays.asList(etabs));
		this.basicUserInfoMap.put(this.currentEtabCodeInfoKey, Arrays.asList(current));
		this.basicUserInfoMap.put(this.structIdsInfoKey, Arrays.asList(structs));
		this.basicUserInfoMap.put(this.currentStructIdInfoKey, Arrays.asList(currentStruct));
		log.debug("basicUserInfoMap : {}", this.basicUserInfoMap);

		this.emptyUserInfoMap.put(this.etabCodesInfoKey, Arrays.asList(new String[] { "1234567b" }));
		this.emptyUserInfoMap.put(this.currentEtabCodeInfoKey, Arrays.asList(new String[] { "1234567B" }));
		this.emptyUserInfoMap.put(this.structIdsInfoKey, Arrays.asList(new String[] { "88888888888888" }));
		this.emptyUserInfoMap.put(this.currentStructIdInfoKey, Arrays.asList(new String[] { "88888888888888" }));
	}

	/**
	 * Retrieve the user info attribute from portlet context, or the Mocked user info
	 * if the system property etablissement-swapper.testEnv = true.
	 *
	 * @param request the portlet request
	 * @param attributeName the attribute to retrieve
	 * @return the user info attribute values
	 */
	@SuppressWarnings("unchecked")
	public List<String> getUserInfo(final PortletRequest request, final String attributeName) {
		Map<String, List<String>> userInfo = (Map<String, List<String>>) request
				.getAttribute("org.jasig.portlet.USER_INFO_MULTIVALUED");

		if ((userInfo == null) && "true".equals(System.getProperty("etablissement-swapper.testEnv"))) {
			userInfo = this.testUserInfoMap;
		}

		List<String> attributeValues = null;

		if (userInfo != null) {
			attributeValues = userInfo.get(attributeName);
		} else {
			log.error("Unable to retrieve Portal UserInfo !");
			//throw new IllegalStateException("Unable to retrieve Portal UserInfo !");
		}

		if (attributeValues == null) {
			attributeValues = Collections.EMPTY_LIST;
		}

		return attributeValues;
	}
}
