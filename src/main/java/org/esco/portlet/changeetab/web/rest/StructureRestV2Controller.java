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

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.esco.portlet.changeetab.model.Structure;
import org.esco.portlet.changeetab.service.IStructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by jgribonvald on 27/03/17.
 */
@Slf4j
@Controller
@RequestMapping(value = "/v2/structures")
public class StructureRestV2Controller {

	@Autowired
	private IStructureService structureService;

	@Autowired
	private SecurityChecker securityChecker;
	/*
	 * Return always Json data (Accept Http Header value has no impact)
	 * example of call : /CONTEXT-PATH/rest/v2/structures/struct/SIREN
	 */
	@RequestMapping(value = "/struct/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<? extends Structure> retrieveStructbInJson(@PathVariable("id") final String id,
			HttpServletRequest request) {
		if (id != null)
			return new ResponseEntity<Structure>(structureService.retrieveStructureById(id), HttpStatus.OK);
		return new ResponseEntity<Structure>(HttpStatus.BAD_REQUEST);
	}

	/*
	 * Return always Json data (Accept Http Header value has no impact)
	 * example of call : /CONTEXT-PATH/rest/v2/structures/structs/?ids=SIREN1,SIREN2
	 */
	@RequestMapping(value = "/structs/", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Map<String, ? extends Structure>> retrieveStructsInJson(
			@RequestParam("ids") final List<String> ids, HttpServletRequest request) {
		if (ids != null && !ids.isEmpty()) {
			return new ResponseEntity<Map<String, ? extends Structure>>(structureService.retrieveStructuresByIds(ids),
					HttpStatus.OK);
		}
		return new ResponseEntity<Map<String, ? extends Structure>>(HttpStatus.BAD_REQUEST);
	}

	/*
	 * Return always Json data (Accept Http Header value has no impact)
	 * example of call : /CONTEXT-PATH/rest/v2/structures/refresh/SIREN
	 */

	@RequestMapping(value = "/refresh/{id}", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Void> refresh(@PathVariable("id") final String id, HttpServletRequest request) {
		if (!securityChecker.isSecureAccess(request)) {
			return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
		}
		if (id != null) {
			structureService.invalidateStructureById(id);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
	}

}
