/**
 * 
 */
package org.esco.portlet.changeetab.dao;

import java.util.Collection;

import org.esco.portlet.changeetab.model.Etablissement;

/**
 * @author GIP RECIA 2013 - Maxime BOSSARD.
 *
 */
public interface IEtablissementDao {

	/**
	 * Find all etablissements.
	 * 
	 * @return a never null Collection which may be empty
	 */
	Collection<Etablissement> findAllEtablissements();

}
