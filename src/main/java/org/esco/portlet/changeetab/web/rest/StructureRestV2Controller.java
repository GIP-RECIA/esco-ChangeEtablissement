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
}
