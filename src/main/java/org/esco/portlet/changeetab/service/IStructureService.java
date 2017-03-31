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
	 * Reload a Structure to consider modifications.
	 *
	 * @param id Id of a Structure
	 * @return a boolean indicating the states of the reload.
	 */
	void reloadStructureById(final String id);

}