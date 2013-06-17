/**
 * 
 */
package org.esco.portlet.changeetab.service;

/**
 * @author GIP RECIA 2013 - Maxime BOSSARD.
 *
 */
public interface IUserService {

	/**
	 * Change the current etablissement of the user.
	 * 
	 * @param userId id of user
	 * @param etabId id of etab
	 */
	void changeCurrentEtablissement(String userId, String etabId);

}
