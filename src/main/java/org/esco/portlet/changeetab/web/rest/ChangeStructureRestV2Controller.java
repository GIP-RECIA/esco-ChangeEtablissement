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
package org.esco.portlet.changeetab.web.rest;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.esco.portlet.changeetab.service.IStructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jgribonvald on 27/03/17.
 */
@Slf4j
@RestController
@RequestMapping(value = "/rest/v2/change")
public class ChangeStructureRestV2Controller {

	@Autowired
	private IStructureService structureService;

	/*
	 * Return always Json data (Accept Http Header value has no impact)
	 * example of call : /CONTEXT-PATH/rest/v2/structures/refresh/SIREN
	 */

	@RequestMapping(value = "/refresh/{id}", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<Void> refresh(@PathVariable("id") final String id, HttpServletRequest request) {
		if (id != null) {
			structureService.invalidateStructureById(id);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
	}

}
