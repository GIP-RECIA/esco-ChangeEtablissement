package org.esco.portlet.changeetab.web.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.esco.portlet.changeetab.model.UniteAdministrativeImmatriculee;
import org.esco.portlet.changeetab.service.IUniteAdministrativeImmatriculeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by jgribonvald on 27/07/16.
 */
@Slf4j
@Controller
@RequestMapping(value = "/v1/structures")
public class StructureRestV1Controller {

	@Autowired
	private IUniteAdministrativeImmatriculeService structureService;

	/*
	 * Return always Json data (Accept Http Header value has no impact)
	 * example of call : /CONTEXT-PATH/rest/v1/structures/etab/UAI
	 */
	@RequestMapping(value = "/etab/{code}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<UniteAdministrativeImmatriculee> retrieveEtabInJson(@PathVariable("code") final String code,
			HttpServletRequest request) {
		if (code != null)
			return new ResponseEntity<UniteAdministrativeImmatriculee>(
					structureService.retrieveEtablissementByCode(code.toUpperCase()), HttpStatus.OK);
		return new ResponseEntity<UniteAdministrativeImmatriculee>(HttpStatus.BAD_REQUEST);
	}

	/*
	 * Return always Json data (Accept Http Header value has no impact)
	 * example of call : /CONTEXT-PATH/rest/v1/structures/etabs/?codes=UAI1,UAI2
	 */
	@RequestMapping(value = "/etabs/", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Map<String, UniteAdministrativeImmatriculee>> retrieveEtabsInJson(
			@RequestParam("codes") final List<String> codes, HttpServletRequest request) {
		if (codes != null && !codes.isEmpty()) {
			Collection<String> converted = new ArrayList<String>(codes.size());
			for (String code : codes) {
				converted.add(code.toUpperCase());
			}
			return new ResponseEntity<Map<String, UniteAdministrativeImmatriculee>>(
					structureService.retrieveEtablissementsByCodes(converted), HttpStatus.OK);
		}
		return new ResponseEntity<Map<String, UniteAdministrativeImmatriculee>>(HttpStatus.BAD_REQUEST);
	}
}
