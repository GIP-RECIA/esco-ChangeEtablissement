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
package org.esco.portlet.changeetab.web.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections.CollectionUtils;
import org.esco.portlet.changeetab.model.Structure;
import org.esco.portlet.changeetab.model.UniteAdministrativeImmatriculee;
import org.esco.portlet.changeetab.service.IUniteAdministrativeImmatriculeService;
import org.esco.portlet.changeetab.service.IUserInfoService;
import org.esco.portlet.changeetab.service.IUserService;
import org.esco.portlet.changeetab.web.ChangeStructCommand;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.ActionMapping;

/**
 * @author GIP RECIA 2013 - Maxime BOSSARD.
 *
 */
@Controller
@RequestMapping("VIEW")
@Slf4j
public class ChangeStructureController implements InitializingBean {

	private static final String STRUCT_LIST_KEY = "structs";

	private static final String CURRENT_STRUCT_KEY = "currentStruct";

	private static final String CURRENT_STRUCT_ATTRIBUTE_ALTERNATIVE_KEY = "currentAttrStructAlternative";

	private static final String CURRENT_STRUCT_ATTRIBUTE_KEY = "currentAttrStruct";

	private static final String DISPLAY_PORTLET_KEY = "displayPortlet";

	private static final String DISPLAY_MODE_KEY = "displayMode";

	private static final String FORCE_DISPLAY_CURRENT_STRUCT_KEY = "displayCurrentStruct";

	private static final String DISPLAY_ONLY_CURRENT_STRUCT_KEY = "displayOnlyCurrentStruct";

	private static final String DISPLAY_ONLY_CURRENT_STRUCT_LOGO_KEY = "displayOnlyCurrentStructLogo";

	private static final String DEFAULT_STRUCT_LOGO_KEY = "defaultStructLogo";

	@Value("${redirectAfterChange:false}")
	@Getter
	@Setter
	private boolean redirectAfterChange = false;

	@Value("${logoutUrlRedirect:null}")
	@Getter
	@Setter
	private String logoutUrlRedirect;

	@Value("${ldap.read.attribute.structureLogo:null}")
	@Getter
	@Setter
	private String ldapLogoInOtherAttributeName;

	@Autowired
	@Getter
	@Setter
	private IUserInfoService userInfoService;

	@Autowired
	@Getter
	@Setter
	private IUniteAdministrativeImmatriculeService structureService;

	@Autowired
	@Getter
	@Setter
	private IUserService userService;

	@ActionMapping("changeStruct")
	public void changeStruct(@ModelAttribute("command") final ChangeStructCommand changeStructCommand,
			final ActionRequest request, final ActionResponse response) throws Exception {
		final String selectedId = changeStructCommand.getSelectedStructId();
		Assert.hasText(selectedId, "No Structure id selected !");

		log.debug("Selected Struct id to change: [{}]", selectedId);

		final Collection<String> changeableStructIds = this.userInfoService.getChangeableStructIds(request);

		final Structure selectedStruct = this.structureService.retrieveStructureById(selectedId);
		if (selectedStruct == null) {
			log.warn("Attempt to switch to a not allowed Structure with id: [{}] !", selectedId);
			changeStructCommand.reset();
			return;
		}

		// TODO remove the if part and keep the else In waiting the attribute of IDS could be undefined
		if (CollectionUtils.isEmpty(changeableStructIds)) {
			String codeFromSelectedId = null;
			if (selectedStruct instanceof UniteAdministrativeImmatriculee) {
				codeFromSelectedId = ((UniteAdministrativeImmatriculee) selectedStruct).getCode();
				final Collection<String> changeableStructCodes = this.userInfoService.getChangeableEtabCodes(request);
				if (!changeableStructCodes.contains(codeFromSelectedId)) {
					// If selected Id is not an allowed Id
					log.warn("Attempt to switch to a not allowed UniteAdministrativeImmatriculee with code: [{}] !",
							codeFromSelectedId);
					log.debug("Allowed Etablissements are: [{}]",
							StringUtils.collectionToCommaDelimitedString(changeableStructCodes));
				} else {
					// Process the etablissement swap.
					final String userId = this.userInfoService.getUserId(request);
					if (StringUtils.hasText(userId)) {
						this.userService.changeCurrentStructure(userId, selectedStruct);

						log.debug("Redirect After Change setted to [{}] with url [{}]", redirectAfterChange,
								logoutUrlRedirect);

						if (this.redirectAfterChange) {
							request.getPortalContext();
							response.sendRedirect(this.logoutUrlRedirect);
						}
					} else {
						log.warn("No user Id found in request ! Cannot process the etablishment swapping !");
					}
				}
			}
		} else {
			if (!changeableStructIds.contains(selectedId)) {
				// If selected Id is not an allowed Id
				log.warn("Attempt to switch to a not allowed Structure with id: [{}] !", selectedId);
				log.debug("Allowed Structure are: [{}]",
						StringUtils.collectionToCommaDelimitedString(changeableStructIds));
			} else {
				// Process the structure swap.
				final String userId = this.userInfoService.getUserId(request);
				if (StringUtils.hasText(userId)) {
					this.userService.changeCurrentStructure(userId, selectedStruct);

					log.debug("Redirect After Change setted to [{}] with url [{}]", redirectAfterChange,
							logoutUrlRedirect);

					if (this.redirectAfterChange) {
						request.getPortalContext();
						response.sendRedirect(this.logoutUrlRedirect);
					}
				} else {
					log.warn("No user Id found in request ! Cannot process the structure swapping !");
				}
			}
		}

		changeStructCommand.reset();
	}

	@RequestMapping
	public ModelAndView handleRenderRequest(final RenderRequest request, final RenderResponse response)
			throws Exception {
		log.debug("Rendering Change Structure portlet View...");
		final boolean displayOnlyCurrent = Boolean.parseBoolean(request.getPreferences().getValue(
				ChangeStructureController.DISPLAY_ONLY_CURRENT_STRUCT_KEY, "false"));

		boolean display = false;

		final String currentEtabCode = this.userInfoService.getCurrentEtabCode(request);
		final Collection<String> changeableEtabCodes = this.userInfoService.getChangeableEtabCodes(request);
		final String currentStructId = this.userInfoService.getCurrentStructId(request);
		final Collection<String> changeableStructIds = this.userInfoService.getChangeableStructIds(request);

		// Case of only showing the current Struct
		if (displayOnlyCurrent) {
			final ModelAndView mv = new ModelAndView("showStruct");
			if (StringUtils.hasText(currentStructId)) {
				final Structure currentStruct = this.structureService.retrieveStructureById(currentStructId);

				log.debug("Show only current structure {}", currentStruct);

				mv.addObject(ChangeStructureController.CURRENT_STRUCT_KEY, currentStruct);
			} else if (StringUtils.hasText(currentEtabCode)) {
				// TODO remove this part when ids will be defined
				final Structure currentEtab = this.structureService.retrieveEtablissementByCode(currentEtabCode);

				log.debug("Show only current etablissement {}", currentEtab);

				mv.addObject(ChangeStructureController.CURRENT_STRUCT_KEY, currentEtab);
			}
			return mv;
		}

		// Case of showing the Logo of the Structure Only
		final boolean displayOnlyCurrentLogo = Boolean.parseBoolean(request.getPreferences().getValue(
				ChangeStructureController.DISPLAY_ONLY_CURRENT_STRUCT_LOGO_KEY, "false"));
		final String defaultLogo = request.getPreferences().getValue(ChangeStructureController.DEFAULT_STRUCT_LOGO_KEY,
				"/images/logoPortal.png");

		if (displayOnlyCurrentLogo) {
			final ModelAndView mv = new ModelAndView("showLogo");
			mv.addObject(ChangeStructureController.CURRENT_STRUCT_ATTRIBUTE_ALTERNATIVE_KEY, defaultLogo);
			if (this.ldapLogoInOtherAttributeName == null) {
				mv.addObject(ChangeStructureController.CURRENT_STRUCT_ATTRIBUTE_KEY, null);
				return mv;
			}
			if (StringUtils.hasText(currentStructId)) {
				final Structure currentStruct = this.structureService.retrieveStructureById(currentStructId);

				log.debug("Show only current structure {}", currentStruct);

				final List<String> logoValues = currentStruct.getOtherAttributes().get(
						this.ldapLogoInOtherAttributeName);

				if (logoValues != null && logoValues.size() == 1) {
					mv.addObject(ChangeStructureController.CURRENT_STRUCT_ATTRIBUTE_KEY, logoValues.get(0));
				} else {
					mv.addObject(ChangeStructureController.CURRENT_STRUCT_ATTRIBUTE_KEY, null);
				}
			} else if (StringUtils.hasText(currentEtabCode)) {
				// TODO remove this part when ids will be defined
				final Structure currentEtab = this.structureService.retrieveEtablissementByCode(currentEtabCode);

				log.debug("Show only current etablissement {}", currentEtab);

				final List<String> logoValues = currentEtab.getOtherAttributes().get(this.ldapLogoInOtherAttributeName);

				if (logoValues != null && logoValues.size() == 1) {
					mv.addObject(ChangeStructureController.CURRENT_STRUCT_ATTRIBUTE_KEY, logoValues.get(0));
				} else {
					mv.addObject(ChangeStructureController.CURRENT_STRUCT_ATTRIBUTE_KEY, null);
				}
			}
			return mv;
		}

		// Case of showing the change current struct features
		final ModelAndView mv = new ModelAndView("home");

		if ((StringUtils.hasText(currentStructId)) && !changeableStructIds.isEmpty()) {
			final Map<String, Structure> changeableStructs = this.structureService
					.retrieveStructuresByIds(changeableStructIds);

			final Structure currentStruct = changeableStructs.remove(currentStructId);

			log.debug("Found [{}] structure whose user can change to.", changeableStructs.size());

			display = !changeableStructs.values().isEmpty();

			mv.addObject(ChangeStructureController.CURRENT_STRUCT_KEY, currentStruct);
			mv.addObject(ChangeStructureController.STRUCT_LIST_KEY, changeableStructs.values());
		} else if ((StringUtils.hasText(currentEtabCode)) && !changeableEtabCodes.isEmpty()) {
			// TODO remove this part when ids will be defined
			final Map<String, UniteAdministrativeImmatriculee> changeableEtabs = this.structureService
					.retrieveEtablissementsByCodes(changeableEtabCodes);

			final Structure currentEtab = changeableEtabs.remove(currentEtabCode);

			log.debug("Found [{}] etablissements whose user can change to.", changeableEtabs.size());

			display = !changeableEtabs.values().isEmpty();

			mv.addObject(ChangeStructureController.CURRENT_STRUCT_KEY, currentEtab);
			mv.addObject(ChangeStructureController.STRUCT_LIST_KEY, changeableEtabs.values());
		}

		mv.addObject(ChangeStructureController.DISPLAY_PORTLET_KEY, display);

		// In this case we force to show the only One Struct associated to the user (if codeEtab is not empty)
		if (StringUtils.hasText(currentStructId) && !display) {
			final Structure currentStruct = this.structureService.retrieveStructureById(currentStructId);

			final String forceDisplay = request.getPreferences().getValue(
					ChangeStructureController.FORCE_DISPLAY_CURRENT_STRUCT_KEY, "false");

			log.debug("Force current structure display = {}, with structure {}", forceDisplay, currentStruct);

			mv.addObject(ChangeStructureController.CURRENT_STRUCT_KEY, currentStruct);
			mv.addObject(ChangeStructureController.FORCE_DISPLAY_CURRENT_STRUCT_KEY, forceDisplay);
		} else if (StringUtils.hasText(currentEtabCode) && !display) {
			// TODO remove this part when ids will be defined
			final Structure currentEtab = this.structureService.retrieveEtablissementByCode(currentEtabCode);

			final String forceDisplay = request.getPreferences().getValue(
					ChangeStructureController.FORCE_DISPLAY_CURRENT_STRUCT_KEY, "false");

			log.debug("Force current etablissement display = {}, with etablissement {}", forceDisplay, currentEtab);

			mv.addObject(ChangeStructureController.CURRENT_STRUCT_KEY, currentEtab);
			mv.addObject(ChangeStructureController.FORCE_DISPLAY_CURRENT_STRUCT_KEY, forceDisplay);
		}

		mv.addObject(ChangeStructureController.DISPLAY_MODE_KEY,
				request.getPreferences().getValue(ChangeStructureController.DISPLAY_MODE_KEY, "All"));

		return mv;
	}

	@ModelAttribute("command")
	public ChangeStructCommand getChangeEtabCommand() {
		return new ChangeStructCommand();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(this.structureService, "No structureService service configured !");
		Assert.notNull(this.userInfoService, "No User Info service configured !");
		Assert.notNull(this.userService, "No User service configured !");

		if (this.redirectAfterChange) {
			Assert.hasText(this.logoutUrlRedirect, "No logout URL to redirect configured !");
		}
	}
}
