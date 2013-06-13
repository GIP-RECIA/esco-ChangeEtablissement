/**
 * 
 */
package org.esco.portlet.changeetab.service;

import java.util.Collection;
import java.util.Map;

import org.esco.portlet.changeetab.model.Etablissement;

/**
 * @author GIP RECIA 2013 - Maxime BOSSARD.
 *
 */
public interface IEtablissementService {

	/**
	 * Return a Collection of Etablissement matching the supplied UAIs.
	 * 
	 * @param ids
	 * @return a never null Map of Id => Etab wich may be empty
	 */
	Map<String, Etablissement> retrieveEtablissementsByIds(Collection<String> ids);

}
