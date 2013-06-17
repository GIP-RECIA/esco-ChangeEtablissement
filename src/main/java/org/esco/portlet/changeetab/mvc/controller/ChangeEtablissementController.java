/**
 * 
 */
package org.esco.portlet.changeetab.mvc.controller;

import java.util.Collection;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.esco.portlet.changeetab.model.Etablissement;
import org.esco.portlet.changeetab.mvc.ChangeEtabCommand;
import org.esco.portlet.changeetab.service.IEtablissementService;
import org.esco.portlet.changeetab.service.IUserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
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
public class ChangeEtablissementController {

	/** Logger. */
	private static final Logger LOG = LoggerFactory.getLogger(ChangeEtablissementController.class);

	private static final String ETAB_LIST_KEY = "etabs";

	private static final String CURRENT_ETAB_KEY = "currentEtab";

	private static final String DISPLAY_PORTLET_KEY = "displayPortlet";

	@Autowired
	private IUserInfoService userInfoService;

	@Autowired
	private IEtablissementService etablissementService;

	@ActionMapping("changeEtab")
	public void changeEtab(@ModelAttribute("command") final ChangeEtabCommand changeEtabCommand, final ActionRequest request) throws Exception {
		final String selectedEtabId = changeEtabCommand.getSelectedEtabId();
		Assert.hasText(selectedEtabId, "No Etablissement Id selected !");

		ChangeEtablissementController.LOG.debug("Selected Etab Id to change: [{}]", selectedEtabId);

		final Collection<String> changeableEtabIds = this.userInfoService.getChangeableEtabIds(request);
		if (!changeableEtabIds.contains(selectedEtabId)) {
			// If selected Id is not an allowed Id
			ChangeEtablissementController.LOG.warn("Attempt to switch to a not allowed Etablissement !");
		}

		changeEtabCommand.reset();
	}

	@RequestMapping
	public ModelAndView handleRenderRequest(final RenderRequest request, final RenderResponse response) throws Exception {
		ChangeEtablissementController.LOG.debug("Rendering Change Etablissement portlet View...");

		final ModelAndView mv = new ModelAndView("home");

		final String currentEtabId = this.userInfoService.getCurrentEtabId(request);
		final Collection<String> changeableEtabIds = this.userInfoService.getChangeableEtabIds(request);

		final Map<String, Etablissement> changeableEtabs = this.etablissementService.retrieveEtablissementsByIds(changeableEtabIds);

		final Etablissement currentEtab = changeableEtabs.remove(currentEtabId);

		ChangeEtablissementController.LOG.debug("Found [{}] etablissements whose user can change to.", changeableEtabs.size());

		mv.addObject(ChangeEtablissementController.DISPLAY_PORTLET_KEY, !changeableEtabs.values().isEmpty());
		mv.addObject(ChangeEtablissementController.CURRENT_ETAB_KEY, currentEtab);
		mv.addObject(ChangeEtablissementController.ETAB_LIST_KEY, changeableEtabs.values());

		return mv;
	}

	@ModelAttribute("command")
	public ChangeEtabCommand getChangeEtabCommand() {
		return new ChangeEtabCommand();
	}

	/**
	 * Getter of userInfoService.
	 *
	 * @return the userInfoService
	 */
	public IUserInfoService getUserInfoService() {
		return this.userInfoService;
	}

	/**
	 * Setter of userInfoService.
	 *
	 * @param userInfoService the userInfoService to set
	 */
	public void setUserInfoService(final IUserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}

	/**
	 * Getter of etablissementService.
	 *
	 * @return the etablissementService
	 */
	public IEtablissementService getEtablissementService() {
		return this.etablissementService;
	}

	/**
	 * Setter of etablissementService.
	 *
	 * @param etablissementService the etablissementService to set
	 */
	public void setEtablissementService(final IEtablissementService etablissementService) {
		this.etablissementService = etablissementService;
	}


}
