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
package org.esco.portlet.changeetab.service;

import java.util.Collection;
import java.util.Map;

import org.esco.portlet.changeetab.model.Structure;

public interface IStructureService {

	/**
	 * Return a Collection of Structure matching the supplied codes.
	 *
	 * @param ids List of ids of structures to retrive
	 * @return a never null Map of Id, only Structure wich may be empty
	 */
	Map<String, Structure> retrieveStructuresByIds(final Collection<String> ids);

	/**
	 * Return a Structure matching the supplied code.
	 *
	 * @param id Id of a Structure to retrieve
	 * @return Struct or null
	 */
	Structure retrieveStructureById(final String id);

	/**
	 * Invalidate a Structure to consider modifications to force reload on.
	 *
	 * @param id Id of a Structure
	 */
	void invalidateStructureById(final String id);

}