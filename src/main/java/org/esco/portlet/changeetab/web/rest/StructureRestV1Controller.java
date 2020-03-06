/**
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jgribonvald on 27/07/16.
 */
@Slf4j
@RestController
@RequestMapping(value = "/rest/v1/structures")
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
