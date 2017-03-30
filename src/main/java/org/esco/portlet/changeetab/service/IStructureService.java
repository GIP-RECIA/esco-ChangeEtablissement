package org.esco.portlet.changeetab.service;

import java.util.Collection;
import java.util.Map;

import org.esco.portlet.changeetab.model.Structure;

public interface IStructureService {

	/**
	 * Return a Collection of Structure matching the supplied codes.
	 *
	 * @param codes List of ids of structures to retrive
	 * @return a never null Map of Id, only Structure wich may be empty
	 */
	public Map<String, Structure> retrieveStructuresByIds(Collection<String> ids);

	/**
	 * Return an Structure matching the supplied code.
	 *
	 * @param code Id of an Structure to retrieve
	 * @return Struct or null
	 */
	public Structure retrieveStructureById(String id);

}