package org.esco.portlet.changeetab.web.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;
import org.esco.portlet.changeetab.dao.IStructureDao;
import org.esco.portlet.changeetab.model.Structure;
import org.esco.portlet.changeetab.service.IStructureService;
import org.esco.portlet.changeetab.service.impl.CachingStructureService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:cachingStructureServiceContext.xml", "classpath:restApiContext.xml"})
@Slf4j
public class StructureRestV2ControllerTest {

    @Autowired
    private CachingStructureService service;

    @SuppressWarnings("unused")
    private IStructureDao mockedDao;

    @Autowired
    private IStructureService structureService;

    private MockMvc restContentMockMvc;

    private List<Structure> structures = new ArrayList<>();

    private Structure structure1;
    private Structure structure2;
    private Structure structure3;

    private String SIREN_1= "SIREN_1";
    private String SIREN_2= "SIREN_3";
    private String SIREN_3= "SIREN_3";

    private Map<String, List<String>> otherAttrs = new HashMap<>();

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);

        StructureRestV2Controller structureRestV2Controller = new StructureRestV2Controller();

        ReflectionTestUtils.setField(structureRestV2Controller, "structureService", structureService);

        this.restContentMockMvc = MockMvcBuilders.standaloneSetup(structureRestV2Controller).build();

        otherAttrs.put("street", Arrays.asList("Adresse 1"));
        otherAttrs.put("phone", Arrays.asList("+33 1 10 10 10 10", "+33 2 20 20 20 20"));

        structure1 = new Structure(SIREN_1, "name1", "name1", "desc1", otherAttrs);
        structure2 = new Structure(SIREN_2, "name2", "name2", "desc2", otherAttrs);
        structure3 = new Structure(SIREN_3, "name3", "name3", "desc3", otherAttrs);
        structures.add(structure1);
        structures.add(structure2);
        structures.add(structure3);
    }

    /**
     * Setter of mockedDao.
     *
     * @param mockedDao the mockedDao to set
     */
    @Autowired
    public void setMockedDao(final IStructureDao mockedDao) {
        this.mockedDao = mockedDao;
        // Init DAO mock
        Mockito.when(mockedDao.findAllStructures()).then(new Answer<Collection<? extends Structure>>() {

            @Override
            public Collection<? extends Structure> answer(InvocationOnMock invocation) throws Throwable {
                return structures;
            }
        });
    }

    @Test
    public void testRefresh() throws Exception {
        restContentMockMvc.perform(
                post("/rest/v2/structures/refresh/" + SIREN_1).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testretrieveStructFromId() throws Exception {
        restContentMockMvc.perform(
                get("/rest/v2/structures/struct/" + SIREN_1).contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").value(SIREN_1))
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.displayName").exists())
                .andExpect(jsonPath("$.description").exists())
                .andExpect(jsonPath("$.otherAttributes").exists())
                .andExpect(jsonPath("$.otherAttributes.street").exists())
                .andExpect(jsonPath("$.otherAttributes.street").isArray())
                .andExpect(jsonPath("$.otherAttributes.street[0]").value(otherAttrs.get("street").get(0)))
                .andExpect(jsonPath("$.otherAttributes.phone").exists())
                .andExpect(jsonPath("$.otherAttributes.phone").isArray())
        ;
    }

}
