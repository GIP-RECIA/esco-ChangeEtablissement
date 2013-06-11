/**
 * 
 */
package org.esco.portlet.changeetab.service;

import java.util.Collection;

import org.esco.portlet.changeetab.model.Etablissement;

/**
 * @author GIP RECIA 2013 - Maxime BOSSARD.
 *
 */
public interface IEtablissementService {

	/**
	 * Return a Collection of Etablissement matching the supplied UAIs.
	 * 
	 * @param uais
	 * @return a never null Collection wich may be empty
	 */
	Collection<Etablissement> retrieveEtablissementsByUais(Collection<String> uais);

}
