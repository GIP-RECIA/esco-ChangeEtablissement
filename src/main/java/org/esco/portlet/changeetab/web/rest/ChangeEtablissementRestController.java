package org.esco.portlet.changeetab.web.rest;

import org.esco.portlet.changeetab.model.Etablissement;
import org.esco.portlet.changeetab.service.IEtablissementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by jgribonvald on 27/07/16.
 */
@Controller
@RequestMapping(value = "/")
public class ChangeEtablissementRestController {

    /** Logger. */
    private static final Logger LOG = LoggerFactory.getLogger(ChangeEtablissementRestController.class);

    @Autowired
	private IEtablissementService etablissementService;

    /*
     * Return always Json data (Accept Http Header value has no impact)
     * example of call : /CONTEXT-PATH/rest/etab/UAI
     */
    @RequestMapping(value="/etab/{code}", method= RequestMethod.GET, produces="application/json")
    public ResponseEntity<Etablissement> retrieveEtabInJson(@PathVariable("code") final String code, HttpServletRequest request) {
        if (code != null)
            return new ResponseEntity<Etablissement>(etablissementService.retrieveEtablissementsByCode(code.toLowerCase()), HttpStatus.OK);
        return new ResponseEntity<Etablissement>(HttpStatus.BAD_REQUEST);
    }

    /*
     * Return always Json data (Accept Http Header value has no impact)
     * example of call : /CONTEXT-PATH/rest/etabs/?codes=UAI1,UAI2
     */
    @RequestMapping(value="/etabs/", method= RequestMethod.GET, produces="application/json")
    public ResponseEntity<Map<String,Etablissement>> retrieveEtabsInJson(@RequestParam("codes") final List<String> codes, HttpServletRequest request) {
        if (codes != null && !codes.isEmpty()) {
            Collection<String> converted = new ArrayList<String>(codes.size());
            for (String code: codes) {
                converted.add(code.toLowerCase());
            }
            return new ResponseEntity<Map<String,Etablissement>>(etablissementService.retrieveEtablissementsByCodes(converted), HttpStatus.OK);
        }
        return new ResponseEntity<Map<String,Etablissement>>(HttpStatus.BAD_REQUEST);
    }
}
