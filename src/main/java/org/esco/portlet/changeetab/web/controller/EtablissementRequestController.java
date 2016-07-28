package org.esco.portlet.changeetab.web.controller;

import org.esco.portlet.changeetab.service.IEtablissementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by jgribonvald on 27/07/16.
 */
@Controller
@Scope("request")
@RequestMapping("VIEW")
public class EtablissementRequestController {

    /** Logger. */
    private static final Logger LOG = LoggerFactory.getLogger(EtablissementRequestController.class);

    @Autowired
	private IEtablissementService etablissementService;

    /**
     * @See https://jira.springsource.org/browse/SPR-7344
     * At the moment, we can't use simply @ResponseBody in portlet mode
     * So we create MappingJacksonJsonView with some code when we need a @ResponseBody stuff.
     * @param object
     * @return
     */
    protected ModelAndView getJacksonView(Object object) {
        MappingJackson2JsonView v = new MappingJackson2JsonView();
        v.setExtractValueFromSingleKeyModel(true);
        ModelAndView modelAndView = new ModelAndView(v);
        modelAndView.addObject(object);
        return modelAndView;
    }

    /*
     * Return always Json data (Accept Http Header value has no impact)
     * example of call : /CONTEXT-PATH/rest/etab/UAI
     */
    @ResourceMapping("etab")
    public ModelAndView retrieveEtabInJson(@RequestParam("code") final String code, ResourceResponse response) {
        if (code != null)
            return getJacksonView(etablissementService.retrieveEtablissementsByCode(code.toLowerCase()));
        response.setProperty(ResourceResponse.HTTP_STATUS_CODE, Integer.toString(HttpServletResponse.SC_BAD_REQUEST));
        return getJacksonView("Bad Request");
    }

    /*
     * Return always Json data (Accept Http Header value has no impact)
     * example of call : /CONTEXT-PATH/rest/etabs/?codes=UAI1,UAI2
     */
    @ResourceMapping("etabs")
    public ModelAndView  retrieveEtabsInJson(@RequestParam("codes") final List<String> codes, ResourceResponse response) {
        if (codes != null && !codes.isEmpty()) {
            Collection<String> converted = new ArrayList<String>(codes.size());
            for (String code: codes) {
                converted.add(code.toLowerCase());
            }
            return getJacksonView(etablissementService.retrieveEtablissementsByCodes(converted));
        }
        response.setProperty(ResourceResponse.HTTP_STATUS_CODE, Integer.toString(HttpServletResponse.SC_BAD_REQUEST));
        return getJacksonView("Bad Request");
    }
}
