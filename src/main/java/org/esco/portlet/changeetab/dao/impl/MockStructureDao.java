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
package org.esco.portlet.changeetab.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections.map.HashedMap;
import org.esco.portlet.changeetab.dao.IStructureDao;
import org.esco.portlet.changeetab.model.Structure;
import org.esco.portlet.changeetab.model.UniteAdministrativeImmatriculee;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

/**
 * @author GIP RECIA 2013 - Maxime BOSSARD.
 *
 */
@Service
@Slf4j
public class MockStructureDao implements IStructureDao, InitializingBean {

	@Override
	public Collection<? extends Structure> findAllStructures() {
		log.debug("Finding all structures ...");

		List<String> valStreet = new ArrayList<String>();
		valStreet.add("au 22 de la rue");
		Map<String, List<String>> map = new HashMap<String, List<String>>(1);
		map.put("street", valStreet);

		final Collection<Structure> allStructs = new HashSet<Structure>(8);
		allStructs.add(new UniteAdministrativeImmatriculee("1234567A", "1234567a", "Etab A", "Etab A", "Desc A", map));
		allStructs.add(new UniteAdministrativeImmatriculee("1234567B", "1234567b", "Etab B", "Etab B", "Desc B", map));
		allStructs.add(new UniteAdministrativeImmatriculee("1234567C", "1234567c", "Etab C", "Etab C", "Desc C",
				new HashedMap()));
		allStructs.add(new UniteAdministrativeImmatriculee("1234567D", "1234567d", "Etab D", "Etab D", "Desc D",
				new HashedMap()));
		allStructs.add(new UniteAdministrativeImmatriculee("1234567E", "1234567e", "Etab E", "Etab E", "Desc E",
				new HashedMap()));
		allStructs.add(new Structure("1234567022251", "Etab F", "Etab F", "Desc F", map));

		log.debug("{} structures found.", allStructs.size());

		return allStructs;
	}

	@Override
	public Structure findOneStructureById(final String id) {
		log.debug("Finding one structure with id {}", id);

		return new UniteAdministrativeImmatriculee("1234567C", "1234567c", "Etab C", "Etab C", "Desc C",
				new HashedMap());

	}

	@Override
	public void afterPropertiesSet() throws Exception {

	}

}
